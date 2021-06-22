package com.ecs.cicd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import software.amazon.awscdk.core.CfnOutput;
import software.amazon.awscdk.core.CfnOutputProps;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.CustomResource;
import software.amazon.awscdk.core.CustomResourceProps;
import software.amazon.awscdk.core.CustomResourceProvider;
import software.amazon.awscdk.core.CustomResourceProviderProps;
import software.amazon.awscdk.core.CustomResourceProviderRuntime;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Names;
import software.amazon.awscdk.core.RemovalPolicy;
import software.amazon.awscdk.core.Size;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.customresources.AwsCustomResource;
import software.amazon.awscdk.customresources.AwsCustomResourcePolicy;
import software.amazon.awscdk.customresources.AwsSdkCall;
import software.amazon.awscdk.customresources.PhysicalResourceId;
import software.amazon.awscdk.services.autoscaling.AutoScalingGroup;
import software.amazon.awscdk.services.autoscaling.AutoScalingGroupProps;
import software.amazon.awscdk.services.autoscaling.BlockDevice;
import software.amazon.awscdk.services.autoscaling.BlockDeviceVolume;
import software.amazon.awscdk.services.autoscaling.EbsDeviceOptions;
import software.amazon.awscdk.services.autoscaling.EbsDeviceVolumeType;
import software.amazon.awscdk.services.autoscaling.HealthCheck;
import software.amazon.awscdk.services.codedeploy.EcsApplication;
import software.amazon.awscdk.services.codedeploy.EcsApplicationProps;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SecurityGroupProps;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcProps;
import software.amazon.awscdk.services.ecs.AsgCapacityProvider;
import software.amazon.awscdk.services.ecs.AsgCapacityProviderProps;
import software.amazon.awscdk.services.ecs.AwsLogDriver;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.CapacityProviderStrategy;
import software.amazon.awscdk.services.ecs.CfnTaskDefinition;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ClusterProps;
import software.amazon.awscdk.services.ecs.ContainerDefinitionOptions;
import software.amazon.awscdk.services.ecs.DeploymentController;
import software.amazon.awscdk.services.ecs.DeploymentControllerType;
import software.amazon.awscdk.services.ecs.Ec2Service;
import software.amazon.awscdk.services.ecs.Ec2ServiceProps;
import software.amazon.awscdk.services.ecs.Ec2TaskDefinition;
import software.amazon.awscdk.services.ecs.Ec2TaskDefinitionProps;
import software.amazon.awscdk.services.ecs.EcsOptimizedImage;
import software.amazon.awscdk.services.ecs.EcsTarget;
import software.amazon.awscdk.services.ecs.ListenerConfig;
import software.amazon.awscdk.services.ecs.PlacementConstraint;
import software.amazon.awscdk.services.ecs.PortMapping;
import software.amazon.awscdk.services.ecs.Protocol;
import software.amazon.awscdk.services.ecs.RepositoryImage;
import software.amazon.awscdk.services.ecs.WindowsOptimizedVersion;
import software.amazon.awscdk.services.elasticloadbalancingv2.AddNetworkTargetsProps;
import software.amazon.awscdk.services.elasticloadbalancingv2.BaseNetworkListenerProps;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkListener;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkListenerAction;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkLoadBalancer;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkLoadBalancerProps;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkTargetGroup;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkTargetGroupProps;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkWeightedTargetGroup;
import software.amazon.awscdk.services.elasticloadbalancingv2.TargetType;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.IRole;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.PolicyStatementProps;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.RoleProps;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketProps;
import software.amazon.awscdk.services.s3.assets.AssetOptions;
import software.amazon.awscdk.services.s3.deployment.BucketDeployment;
import software.amazon.awscdk.services.s3.deployment.BucketDeploymentProps;
import software.amazon.awscdk.services.s3.deployment.Source;

import static com.ecs.cicd.DeploymentGroupRequestCreator.createDeploymentGroupParam;
import static com.ecs.cicd.DeploymentGroupRequestCreator.mapper;
import static com.ecs.cicd.DeploymentGroupRequestCreator.updateDeploymentGroup;
import static com.ecs.cicd.FileZipperHelper.writeFileAsZipIfChanged;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class EcsWindowsBlueGreenStack extends Stack {

    public EcsWindowsBlueGreenStack(final Construct scope, final String id) throws IOException {
        this(scope, id, null);
    }

    public EcsWindowsBlueGreenStack(final Construct scope, final String id, final StackProps props) throws IOException {
        super(scope, id, props);

        Vpc vpc = new Vpc(this, "ECSWindowsVpc", VpcProps.builder()
                .maxAzs(2)
                .build());

        Cluster cluster = new Cluster(this, "WindowsCluster", ClusterProps.builder()
                .vpc(vpc)
                .containerInsights(true)
                .build());

        String clusterUniqueId = Names.uniqueId(cluster);
        String clusterSubUniqueId = clusterUniqueId.substring(clusterUniqueId.length() - 4);

        SecurityGroup asgSecurityGroup = new SecurityGroup(this, "AsgSecurityGroup", SecurityGroupProps.builder()
                .vpc(vpc)
                .description("Security group for ASG launched instances")
                .build());

        asgSecurityGroup.getConnections().allowFromAnyIpv4(Port.tcp(8080));

        AutoScalingGroup windowsEcsAutoScalingGroup = new AutoScalingGroup(this, "AutoScalingGroupWindowsCluster", AutoScalingGroupProps.builder()
                .healthCheck(HealthCheck.ec2())
                .blockDevices(singletonList(
                        BlockDevice.builder()
                                .volume(BlockDeviceVolume.ebs(50, EbsDeviceOptions.builder()
                                        .volumeType(EbsDeviceVolumeType.GP2)
                                        .build()))
                                .deviceName("/dev/sda1")
                                .build()
                ))
                .maxCapacity(6)
                .vpc(vpc)
                .securityGroup(asgSecurityGroup)
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.LARGE))
                .machineImage(EcsOptimizedImage.windows(WindowsOptimizedVersion.SERVER_2019))
                .build());

        String capacityProviderName = "WindowsClusterCapacity-" + clusterSubUniqueId;

        AsgCapacityProvider windowsClusterCapacityProvider = new AsgCapacityProvider(this, "WindowsClusterAsg", AsgCapacityProviderProps.builder()
                .autoScalingGroup(windowsEcsAutoScalingGroup)
                .enableManagedScaling(true)
                .capacityProviderName(capacityProviderName)
                .build());

        cluster.addAsgCapacityProvider(windowsClusterCapacityProvider);

        Role taskRole = new Role(this, "ecs-taskRole" + clusterSubUniqueId, RoleProps.builder()
                .assumedBy(new ServicePrincipal("ecs-tasks.amazonaws.com"))
                .build());

        PolicyStatement executionRolePolicy = new PolicyStatement(PolicyStatementProps.builder()
                .effect(Effect.ALLOW)
                .resources(singletonList("*"))
                .actions(asList(
                        "ecr:GetAuthorizationToken",
                        "ecr:BatchCheckLayerAvailability",
                        "ecr:GetDownloadUrlForLayer",
                        "ecr:BatchGetImage",
                        "logs:CreateLogStream",
                        "logs:PutLogEvents"
                ))
                .build());

        AwsLogDriver awsLogDriver = new AwsLogDriver(AwsLogDriverProps.builder()
                .streamPrefix("ecs-windows-logs")
                .build());

        Ec2TaskDefinition windowsContainerTaskDef = new Ec2TaskDefinition(this, "WindowsContainerTaskDef", Ec2TaskDefinitionProps.builder()
                .taskRole(taskRole)
                .networkMode(null)
                .family("windows-simple-iis")
                .build());

        //NOTE: You must explicitly change the task as the cdk does not support <default> mode
        if (windowsContainerTaskDef.getNode().getDefaultChild() instanceof CfnTaskDefinition) {
            CfnTaskDefinition defaultChild = (CfnTaskDefinition) windowsContainerTaskDef.getNode().getDefaultChild();
            defaultChild.setNetworkMode(null);
        }

        windowsContainerTaskDef.addToExecutionRolePolicy(executionRolePolicy);

        String containerName = "windows_sample_app";
        int containerPort = 80;

        windowsContainerTaskDef.addContainer("WindowsContainer", ContainerDefinitionOptions.builder()
                .entryPoint(asList(
                        "powershell",
                        "-Command"
                ))
                .command(singletonList(
                        "New-Item -Path C:\\inetpub\\wwwroot\\index.html -ItemType file -Value '<html> <head> <title>Amazon ECS Sample App</title> <style>body {margin-top: 40px; background-color: #333;} </style> </head><body> <div style=color:white;text-align:center> <h1>Amazon ECS Sample App</h1> <h2>Congratulations!</h2> <p>Your application is now running on a container in Amazon ECS.</p>' -Force ; C:\\ServiceMonitor.exe w3svc"
                ))
                .containerName(containerName)
                .image(RepositoryImage.fromRegistry("microsoft/iis"))
                .cpu(512)
                .memoryLimitMiB(1024)
                .essential(true)
                .portMappings(singletonList(
                        PortMapping.builder()
                                .containerPort(containerPort)
                                .hostPort(8080)
                                .protocol(Protocol.TCP)
                                .build()
                ))
                .logging(awsLogDriver)
                .build());


        NetworkLoadBalancer serviceNlb = new NetworkLoadBalancer(this, "BgServiceNLB", NetworkLoadBalancerProps.builder()
                .internetFacing(true)
                .vpc(vpc)
                .vpcSubnets(SubnetSelection.builder()
                        .subnetType(SubnetType.PUBLIC)
                        .build())
                .build());

        String nlbUniqueId = Names.uniqueId(serviceNlb);
        String nlbSubUniqueId = nlbUniqueId.substring(nlbUniqueId.length() - 4);

        String blueTarget = "BlueTarget" + nlbSubUniqueId;
        String targetGreen = "GreenTarget" + nlbSubUniqueId;

        NetworkWeightedTargetGroup greenTarget = NetworkWeightedTargetGroup.builder()
                .targetGroup(new NetworkTargetGroup(this, targetGreen, NetworkTargetGroupProps.builder()
                        .port(80)
                        .targetGroupName(targetGreen)
                        .targetType(TargetType.INSTANCE)
                        .protocol(software.amazon.awscdk.services.elasticloadbalancingv2.Protocol.TCP)
                        .vpc(vpc)
                        .build()))
                .build();

        NetworkListener productionListener = serviceNlb.addListener("ProductionListener", BaseNetworkListenerProps.builder()
                .port(80)
                .build());

        NetworkListener greenListener = serviceNlb.addListener("GreenListener", BaseNetworkListenerProps.builder()
                .port(9000)
                .defaultAction(NetworkListenerAction.weightedForward(singletonList(
                        greenTarget)))
                .build());

        Ec2Service service = new Ec2Service(this, "bg-Service", Ec2ServiceProps.builder()
                .cluster(cluster)
                .daemon(false)
                .taskDefinition(windowsContainerTaskDef)
                .deploymentController(DeploymentController.builder()
                        .type(DeploymentControllerType.CODE_DEPLOY)
                        .build())
                .placementConstraints(singletonList(PlacementConstraint.distinctInstances()))
                .desiredCount(2)
                .capacityProviderStrategies(singletonList(CapacityProviderStrategy.builder()
                        .capacityProvider(windowsClusterCapacityProvider.getCapacityProviderName())
                        .weight(1)
                        .build()))
                .build());

        service.registerLoadBalancerTargets(EcsTarget.builder()
                .listener(ListenerConfig.networkListener(productionListener, AddNetworkTargetsProps.builder()
                        .targetGroupName(blueTarget)
                        .port(80)
                        .build()))
                .containerName(containerName)
                .containerPort(80)
                .newTargetGroupId(blueTarget)
                .build());

        // Create code deploy application with a deployment group

        EcsApplication ecsApplication = new EcsApplication(this, "BgDeploy", EcsApplicationProps.builder()
                .applicationName("BgDeploy-" + clusterSubUniqueId)
                .build());

        Role serviceRole = new Role(this, "AWSCodeDeployRoleForECS", RoleProps.builder()
                .assumedBy(new ServicePrincipal("codedeploy.amazonaws.com"))
                .managedPolicies(singletonList(ManagedPolicy.fromAwsManagedPolicyName("AWSCodeDeployRoleForECS")))
                .build());

        String deploymentGroupName = "BgDeploymentGroup";

        // https://docs.aws.amazon.com/AWSJavaScriptSDK/latest/AWS/ECS.html#describeTaskDefinition-property
        AwsSdkCall createDgCall = AwsSdkCall.builder()
                .service("CodeDeploy")
                .action("createDeploymentGroup")
                .apiVersion("2014-10-06")
                .parameters(createDeploymentGroupParam(ecsApplication,
                        serviceRole,
                        productionListener,
                        greenListener,
                        blueTarget,
                        targetGreen,
                        cluster.getClusterName(),
                        service.getServiceName(),
                        deploymentGroupName))
                .physicalResourceId(PhysicalResourceId.of("CreateDeploymentGroup"))
                .build();

        AwsSdkCall updateDgCall = AwsSdkCall.builder()
                .service("CodeDeploy")
                .action("updateDeploymentGroup")
                .apiVersion("2014-10-06")
                .parameters(updateDeploymentGroup(ecsApplication,
                        serviceRole,
                        productionListener,
                        greenListener,
                        blueTarget,
                        targetGreen,
                        cluster.getClusterName(),
                        service.getServiceName(),
                        deploymentGroupName))
                .physicalResourceId(PhysicalResourceId.of("UpdateDeploymentGroup"))
                .build();

        AwsCustomResource.Builder.create(this, "CreateDeploymentGroup")
                .onCreate(createDgCall)
                .onUpdate(updateDgCall)
                .policy(AwsCustomResourcePolicy.fromStatements(Arrays.asList(
                        PolicyStatement.Builder.create()
                                .actions(Arrays.asList(
                                        "codedeploy:CreateDeploymentGroup",
                                        "codedeploy:UpdateDeploymentGroup",
                                        "codedeploy:DeleteDeploymentGroup"
                                ))
                                .effect(Effect.ALLOW)
                                .resources(singletonList(
                                        "*"
                                ))
                                .build(),
                        PolicyStatement.Builder.create()
                                .effect(Effect.ALLOW)
                                .actions(singletonList(
                                        "iam:PassRole"
                                ))
                                .resources(singletonList(
                                        serviceRole.getRoleArn()
                                ))
                                .build()
                )))
                .logRetention(RetentionDays.FIVE_DAYS)
                .build();

        Bucket bgServiceCiCdBucket = new Bucket(this, "bg-service-cicd-bucket", BucketProps.builder()
                .versioned(true)
                .removalPolicy(RemovalPolicy.DESTROY)
                .build());

        Path path = Paths.get("asset/appspec_template.yaml");

        String appspec = Files.readString(path).replace("<CONTAINER_NAME>", containerName)
                .replace("<CONTAINER_PORT>", String.valueOf(containerPort))
                .replace("<CAPACITY_PROVIDER>", capacityProviderName);

        writeFileAsZipIfChanged(appspec);

        new BucketDeployment(this, "CopyAppSecFile", BucketDeploymentProps.builder()
                .destinationBucket(bgServiceCiCdBucket)
                .prune(false)
                .sources(singletonList(Source.asset("asset", AssetOptions.builder()
                        .exclude(asList("**", "!appspec.zip"))
                        .build())))
                .build());

        // Task definition(taskdef.json) and Appspec(appspec_template.yaml) copying
        // https://github.com/aws/aws-cdk/issues/8304 until cdk provides this natively
        Map<String, Object> properties = new HashMap<>();
        properties.put("s3Bucket", bgServiceCiCdBucket.getBucketName());
        properties.put("s3Key", "taskdef.zip");
        properties.put("imagePlaceHolder", "<IMAGE1_NAME>");
        properties.put("InstallLatestAwsSdk", true);
        properties.put("TaskDefUniqueId", Names.uniqueId(windowsContainerTaskDef));
        properties.put("ServiceName", Names.uniqueId(service));

        HashMap<String, String> param = new HashMap<>();
        param.put("taskDefinition", windowsContainerTaskDef.getTaskDefinitionArn());

        AwsSdkCall getTaskDef = AwsSdkCall.builder()
                .service("ECS")
                .action("describeTaskDefinition")
                .apiVersion("2014-11-13")
                .parameters(param)
                .physicalResourceId(PhysicalResourceId.of("FetchTaskDef"))
                .build();

        String propAsMap = mapper().writeValueAsString(getTaskDef);

        properties.put("Create", propAsMap);
        properties.put("Update", propAsMap);
        properties.put("Delete", "");

        PolicyStatement policyStatement = new PolicyStatement(PolicyStatementProps.builder()
                .resources(singletonList("*"))
                .actions(singletonList("ecs:DescribeTaskDefinition"))
                .effect(Effect.ALLOW)
                .build());

        Role fetchTaskDefProviderFunctionRole = new Role(this, "FetchTaskDefProviderFunctionRole", RoleProps.builder()
                .assumedBy(new ServicePrincipal("lambda.amazonaws.com"))
                .build());

        fetchTaskDefProviderFunctionRole.addToPolicy(policyStatement);
        bgServiceCiCdBucket.grantReadWrite(fetchTaskDefProviderFunctionRole);

        CustomResourceProvider fetchTaskDefProvider = CustomResourceProvider.getOrCreateProvider(this,
                "FetchTaskDefProvider",
                CustomResourceProviderProps.builder()
                        .codeDirectory("lambda")
                        .description("Function to fetch task def")
                        .runtime(CustomResourceProviderRuntime.NODEJS_14_X)
                        .timeout(Duration.minutes(1))
                        .policyStatements(singletonList(policyStatement.toJSON()))
                        .memorySize(Size.mebibytes(512))
                        .build());

        CustomResource fetchTaskDefJson = new CustomResource(this, "FetchTaskDefJson", CustomResourceProps.builder()
                .serviceToken(fetchTaskDefProvider.getServiceToken())
                .properties(properties)
                .build());

        fetchTaskDefJson.getNode().addDependency(service);

        IRole taskDefLambdaRole = Role.fromRoleArn(this, "TaskDefLambdaRole", fetchTaskDefProvider.getRoleArn());

        bgServiceCiCdBucket.grantReadWrite(taskDefLambdaRole);

        new CfnOutput(this, "TaskDefArn", CfnOutputProps.builder()
                .value(windowsContainerTaskDef.getTaskDefinitionArn())
                .description("TaskDefinition Arn")
                .build());

        new CfnOutput(this, "LoadBalancerDnsName", CfnOutputProps.builder()
                .value(serviceNlb.getLoadBalancerDnsName())
                .description("Application URL")
                .build());

        new CfnOutput(this, "ArtifactSourceBucketArn", CfnOutputProps.builder()
                .value(bgServiceCiCdBucket.getBucketArn())
                .exportName("ArtifactSourceBucketArn")
                .description("ArtifactSourceBucketArn")
                .build());

        new CfnOutput(this, "CodeDeployDeploymentGroupName", CfnOutputProps.builder()
                .value(deploymentGroupName)
                .exportName("CodeDeployDeploymentGroupName")
                .description("CodeDeployDeploymentGroupName")
                .build());

        new CfnOutput(this, "CodeDeployApplicationName", CfnOutputProps.builder()
                .value(ecsApplication.getApplicationName())
                .exportName("CodeDeployApplicationName")
                .description("CodeDeployApplicationName")
                .build());
    }
}
