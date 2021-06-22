package com.ecs.cicd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Fn;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.cloudtrail.AddEventSelectorOptions;
import software.amazon.awscdk.services.cloudtrail.S3EventSelector;
import software.amazon.awscdk.services.cloudtrail.Trail;
import software.amazon.awscdk.services.cloudtrail.TrailProps;
import software.amazon.awscdk.services.codecommit.IRepository;
import software.amazon.awscdk.services.codecommit.Repository;
import software.amazon.awscdk.services.codepipeline.CfnPipeline;
import software.amazon.awscdk.services.codepipeline.CfnPipelineProps;
import software.amazon.awscdk.services.events.CfnRule;
import software.amazon.awscdk.services.events.CfnRuleProps;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.PolicyDocument;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.RoleProps;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.IBucket;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static software.amazon.awscdk.services.cloudtrail.ReadWriteType.WRITE_ONLY;
import static software.amazon.awscdk.services.codepipeline.CfnPipeline.ActionDeclarationProperty;
import static software.amazon.awscdk.services.codepipeline.CfnPipeline.ActionTypeIdProperty;
import static software.amazon.awscdk.services.codepipeline.CfnPipeline.ArtifactStoreProperty;
import static software.amazon.awscdk.services.codepipeline.CfnPipeline.InputArtifactProperty;
import static software.amazon.awscdk.services.codepipeline.CfnPipeline.OutputArtifactProperty;
import static software.amazon.awscdk.services.codepipeline.CfnPipeline.StageDeclarationProperty;
import static software.amazon.awscdk.services.events.CfnRule.TargetProperty;

public class PipelineEcsWindowsBlueGreenStack extends Stack {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(Include.NON_EMPTY);
    }

    public PipelineEcsWindowsBlueGreenStack(final Construct scope, final String id, final StackProps props) throws IOException {
        super(scope, id, props);

        String policyJson = Files.readString(Paths.get("asset/codepipeline-default-service-policy.json"));

        PolicyDocument document = PolicyDocument.fromJson(new ObjectMapper().readTree(policyJson));

        ManagedPolicy servicePolicy = ManagedPolicy.Builder.create(this, "BgCodePipelineRoleDefaultServicePolicy")
                .document(document)
                .build();

        Role role = new Role(this, "BgCodePipelineRole", RoleProps.builder()
                .assumedBy(new ServicePrincipal("codepipeline.amazonaws.com"))
                .managedPolicies(singletonList(servicePolicy))
                .build());

        IBucket sourceArtifactBucket = Bucket.fromBucketArn(this,
                "ArtifactSourceBucket",
                Fn.importValue("ArtifactSourceBucketArn"));

        String codeDeployApplicationName = Fn.importValue("CodeDeployApplicationName");
        String codeDeployDeploymentGroupName = Fn.importValue("CodeDeployDeploymentGroupName");

        IRepository sourceImageArtifact = Repository.fromRepositoryName(this, "SourceImageArtifact", String.valueOf(this.getNode().tryGetContext("imageRepository")));

        Trail trail = new Trail(this, "ArtifactSourceBucketTrail", TrailProps.builder()
                .trailName("ArtifactSourceBucketTrail")
                .build());

        Map<String, Object> detail = Map.ofEntries(
                Map.entry("eventSource", singletonList("s3.amazonaws.com")),
                Map.entry("eventName", asList("PutObject", "CompleteMultipartUpload", "CopyObject")),
                Map.entry("requestParameters", Map.ofEntries(
                        Map.entry("bucketName", singletonList(sourceArtifactBucket.getBucketName()))
                ))
        );

        trail.addS3EventSelector(singletonList(S3EventSelector.builder()
                        .bucket(sourceArtifactBucket)
                        .objectPrefix("taskdef.zip")
                        .build()),
                AddEventSelectorOptions.builder()
                        .readWriteType(WRITE_ONLY)
                        .build());

        trail.addS3EventSelector(singletonList(S3EventSelector.builder()
                        .bucket(sourceArtifactBucket)
                        .objectPrefix("appspec.zip")
                        .build()),
                AddEventSelectorOptions.builder()
                        .readWriteType(WRITE_ONLY)
                        .build());

        Bucket bgCodePipelineArtifact = Bucket.Builder.create(this, "BgCodePipelineArtifact")
                .autoDeleteObjects(true)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build();

        Map<String, Object> taskDefSourceConfig = Map.ofEntries(
                Map.entry("S3Bucket", sourceArtifactBucket.getBucketName()),
                Map.entry("S3ObjectKey", "taskdef.zip"),
                Map.entry("PollForSourceChanges", "false")
        );

        ActionDeclarationProperty taskDefSource = ActionDeclarationProperty.builder()
                .actionTypeId(ActionTypeIdProperty.builder()
                        .category("Source")
                        .owner("AWS")
                        .provider("S3")
                        .version("1")
                        .build())
                .configuration(taskDefSourceConfig)
                .name("TaskDefSource")
                .outputArtifacts(singletonList(OutputArtifactProperty.builder()
                        .name("TaskDefSourceArtifact")
                        .build()))
                .build();

        Map<String, Object> appSpecSourceConfig = Map.ofEntries(
                Map.entry("S3Bucket", sourceArtifactBucket.getBucketName()),
                Map.entry("S3ObjectKey", "appspec.zip"),
                Map.entry("PollForSourceChanges", "false")
        );

        ActionDeclarationProperty appSpecSource = ActionDeclarationProperty.builder()
                .actionTypeId(ActionTypeIdProperty.builder()
                        .category("Source")
                        .owner("AWS")
                        .provider("S3")
                        .version("1")
                        .build())
                .configuration(appSpecSourceConfig)
                .name("AppSpecSource")
                .outputArtifacts(singletonList(OutputArtifactProperty.builder()
                        .name("AppSpecSourceArtifact")
                        .build()))
                .build();

        Map<String, Object> ecrSourceConfig = Map.ofEntries(
                Map.entry("RepositoryName", sourceImageArtifact.getRepositoryName())
        );

        ActionDeclarationProperty imageSource = ActionDeclarationProperty.builder()
                .actionTypeId(ActionTypeIdProperty.builder()
                        .category("Source")
                        .owner("AWS")
                        .provider("ECR")
                        .version("1")
                        .build())
                .configuration(ecrSourceConfig)
                .name("Image")
                .outputArtifacts(singletonList(OutputArtifactProperty.builder()
                        .name("ImageArtifact")
                        .build()))
                .build();

        StageDeclarationProperty sourceStage = StageDeclarationProperty.builder()
                .name("Source")
                .actions(asList(taskDefSource, appSpecSource, imageSource))
                .build();

        Map<String, Object> configuration = Map.ofEntries(
                Map.entry("ApplicationName", codeDeployApplicationName),
                Map.entry("DeploymentGroupName", codeDeployDeploymentGroupName),
                Map.entry("TaskDefinitionTemplateArtifact", "TaskDefSourceArtifact"),
                Map.entry("AppSpecTemplateArtifact", "AppSpecSourceArtifact"),
                Map.entry("Image1ArtifactName", "ImageArtifact"),
                Map.entry("Image1ContainerName", "IMAGE1_NAME")
        );

        ActionDeclarationProperty ecsBgDeploy = ActionDeclarationProperty.builder()
                .actionTypeId(ActionTypeIdProperty.builder()
                        .category("Deploy")
                        .owner("AWS")
                        .provider("CodeDeployToECS")
                        .version("1")
                        .build())
                .inputArtifacts(asList(
                        InputArtifactProperty.builder()
                                .name("TaskDefSourceArtifact")
                                .build(),
                        InputArtifactProperty.builder()
                                .name("AppSpecSourceArtifact")
                                .build(),
                        InputArtifactProperty.builder()
                                .name("ImageArtifact")
                                .build()))
                .configuration(configuration)
                .name("BgDeploy")
                .namespace("DeployVariables")
                .build();

        StageDeclarationProperty deployStage = StageDeclarationProperty.builder()
                .name("Deploy")
                .actions(singletonList(ecsBgDeploy))
                .build();

        CfnPipeline cfnPipeline = new CfnPipeline(this, "BgPipeline", CfnPipelineProps.builder()
                .stages(asList(sourceStage, deployStage))
                .artifactStore(ArtifactStoreProperty.builder()
                        .location(bgCodePipelineArtifact.getBucketName())
                        .type("S3")
                        .build())
                .roleArn(role.getRoleArn())
                .build());

        String codePipelineArn = String.format("arn:aws:codepipeline:%s:%s:%s", this.getRegion(), this.getAccount(), cfnPipeline.getRef());

        Role startPipelineRole = Role.Builder.create(this, "StartPipelineRole")
                .assumedBy(new ServicePrincipal("events.amazonaws.com"))
                .build();

        startPipelineRole.addToPolicy(PolicyStatement.Builder.create()
                .actions(singletonList("codepipeline:StartPipelineExecution"))
                .resources(singletonList(codePipelineArn))
                .effect(Effect.ALLOW)
                .build());

        String eventPattern = Files.readString(Paths.get("asset/s3rule.json"))
                .replace("BUCKET_NAME", sourceArtifactBucket.getBucketName())
                .replace("BUCKET_KEY", "taskdef.zip");


        new CfnRule(this, "TriggerCodePipelineTaskDef", CfnRuleProps.builder()
                .description("Rule to trigger code pipeline on s3 bucket taskdef")
                .name("BgPipelineS3TriggerTaskDef")
                .eventPattern(eventPattern)
                .targets(singletonList(TargetProperty.builder()
                        .arn(codePipelineArn)
                        .id("BgCopePipelineTarget")
                        .roleArn(startPipelineRole.getRoleArn())
                        .build()))
                .build());

        eventPattern = Files.readString(Paths.get("asset/s3rule.json"))
                .replace("BUCKET_NAME", sourceArtifactBucket.getBucketName())
                .replace("BUCKET_KEY", "appspec.zip");

        new CfnRule(this, "TriggerCodePipelineAppSpec", CfnRuleProps.builder()
                .description("Rule to trigger code pipeline on s3 bucket Appspec")
                .name("BgPipelineS3TriggerAppSpec")
                .eventPattern(eventPattern)
                .targets(singletonList(TargetProperty.builder()
                        .arn(codePipelineArn)
                        .id("BgCopePipelineTarget")
                        .roleArn(startPipelineRole.getRoleArn())
                        .build()))
                .build());
    }
}
