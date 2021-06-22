package com.ecs.cicd;

import java.util.Map;

import com.amazonaws.services.codedeploy.model.BlueGreenDeploymentConfiguration;
import com.amazonaws.services.codedeploy.model.BlueInstanceTerminationOption;
import com.amazonaws.services.codedeploy.model.DeploymentOption;
import com.amazonaws.services.codedeploy.model.DeploymentReadyAction;
import com.amazonaws.services.codedeploy.model.DeploymentReadyOption;
import com.amazonaws.services.codedeploy.model.DeploymentStyle;
import com.amazonaws.services.codedeploy.model.DeploymentType;
import com.amazonaws.services.codedeploy.model.ECSService;
import com.amazonaws.services.codedeploy.model.InstanceAction;
import com.amazonaws.services.codedeploy.model.LoadBalancerInfo;
import com.amazonaws.services.codedeploy.model.TargetGroupInfo;
import com.amazonaws.services.codedeploy.model.TargetGroupPairInfo;
import com.amazonaws.services.codedeploy.model.TrafficRoute;
import com.ecs.cicd.model.CreateDeploymentGroupRequest;
import com.ecs.cicd.model.DeleteDeploymentGroupRequest;
import com.ecs.cicd.model.UpdateDeploymentGroupRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awscdk.services.codedeploy.EcsApplication;
import software.amazon.awscdk.services.elasticloadbalancingv2.NetworkListener;
import software.amazon.awscdk.services.iam.Role;

public class DeploymentGroupRequestCreator {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    private DeploymentGroupRequestCreator() {
    }

    static Object createDeploymentGroupParam(EcsApplication ecsApplication,
                                          Role serviceRole,
                                          NetworkListener productionListener,
                                          NetworkListener greenListener,
                                          String blueTarget,
                                          String targetGreen,
                                          String clusterName,
                                          String serviceName,
                                          String deploymentGroupName) {

        LoadBalancerInfo loadBalancerInfo = new LoadBalancerInfo()
                .withTargetGroupPairInfoList(new TargetGroupPairInfo()
                        .withProdTrafficRoute(new TrafficRoute()
                                .withListenerArns(productionListener.getListenerArn()))
                        .withTestTrafficRoute(new TrafficRoute()
                                .withListenerArns(greenListener.getListenerArn()))
                        .withTargetGroups(new TargetGroupInfo()
                                        .withName(blueTarget),
                                new TargetGroupInfo()
                                        .withName(targetGreen)));

        CreateDeploymentGroupRequest deploymentGroup = new CreateDeploymentGroupRequest().withApplicationName(ecsApplication.getApplicationName())
                .withDeploymentGroupName(deploymentGroupName)
                .withServiceRoleArn(serviceRole.getRoleArn())
                .withDeploymentStyle(new DeploymentStyle()
                        .withDeploymentOption(DeploymentOption.WITH_TRAFFIC_CONTROL)
                        .withDeploymentType(DeploymentType.BLUE_GREEN))
                .withBlueGreenDeploymentConfiguration(new BlueGreenDeploymentConfiguration()
                        .withTerminateBlueInstancesOnDeploymentSuccess(new BlueInstanceTerminationOption()
                                .withAction(InstanceAction.TERMINATE)
                                .withTerminationWaitTimeInMinutes(5))
                        .withDeploymentReadyOption(new DeploymentReadyOption()
                                .withActionOnTimeout(DeploymentReadyAction.CONTINUE_DEPLOYMENT)))
                .withEcsServices(new ECSService()
                        .withServiceName(serviceName)
                        .withClusterName(clusterName))
                .withLoadBalancerInfo(loadBalancerInfo);

        return MAPPER.convertValue(deploymentGroup, Map.class);
    }

    static Object updateDeploymentGroup(EcsApplication ecsApplication,
                                      Role serviceRole,
                                      NetworkListener productionListener,
                                      NetworkListener greenListener,
                                      String blueTarget,
                                      String targetGreen,
                                      String clusterName,
                                      String serviceName,
                                      String deploymentGroupName) {

        LoadBalancerInfo loadBalancerInfo = new LoadBalancerInfo()
                .withTargetGroupPairInfoList(new TargetGroupPairInfo()
                        .withProdTrafficRoute(new TrafficRoute()
                                .withListenerArns(productionListener.getListenerArn()))
                        .withTestTrafficRoute(new TrafficRoute()
                                .withListenerArns(greenListener.getListenerArn()))
                        .withTargetGroups(new TargetGroupInfo()
                                        .withName(blueTarget),
                                new TargetGroupInfo()
                                        .withName(targetGreen)));

        UpdateDeploymentGroupRequest deploymentGroup = new UpdateDeploymentGroupRequest()
                .withApplicationName(ecsApplication.getApplicationName())
                .withCurrentDeploymentGroupName(deploymentGroupName)
                .withServiceRoleArn(serviceRole.getRoleArn())
                .withDeploymentStyle(new DeploymentStyle()
                        .withDeploymentOption(DeploymentOption.WITH_TRAFFIC_CONTROL)
                        .withDeploymentType(DeploymentType.BLUE_GREEN))
                .withBlueGreenDeploymentConfiguration(new BlueGreenDeploymentConfiguration()
                        .withTerminateBlueInstancesOnDeploymentSuccess(new BlueInstanceTerminationOption()
                                .withAction(InstanceAction.TERMINATE)
                                .withTerminationWaitTimeInMinutes(5))
                        .withDeploymentReadyOption(new DeploymentReadyOption()
                                .withActionOnTimeout(DeploymentReadyAction.CONTINUE_DEPLOYMENT)))
                .withEcsServices(new ECSService()
                        .withServiceName(serviceName)
                        .withClusterName(clusterName))
                .withLoadBalancerInfo(loadBalancerInfo);

        return MAPPER.convertValue(deploymentGroup, Map.class);
    }

    static Object deleteGroupParam(String applicationName,
                                String deploymentGroupName) {

        DeleteDeploymentGroupRequest deleteDeploymentGroupRequest = new DeleteDeploymentGroupRequest()
                .withApplicationName(applicationName)
                .withDeploymentGroupName(deploymentGroupName);

        return MAPPER.convertValue(deleteDeploymentGroupRequest, Map.class);
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }
}
