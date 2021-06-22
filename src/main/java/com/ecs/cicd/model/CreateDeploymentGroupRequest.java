package com.ecs.cicd.model;

import java.io.Serializable;

import com.amazonaws.services.codedeploy.model.BlueGreenDeploymentConfiguration;
import com.amazonaws.services.codedeploy.model.DeploymentStyle;
import com.amazonaws.services.codedeploy.model.ECSService;
import com.amazonaws.services.codedeploy.model.LoadBalancerInfo;

public class CreateDeploymentGroupRequest implements Serializable, Cloneable {

    /**
     * <p>
     * The name of an AWS CodeDeploy application associated with the IAM user or AWS account.
     * </p>
     */
    private String applicationName;
    /**
     * <p>
     * The name of a new deployment group for the specified application.
     * </p>
     */
    private String deploymentGroupName;
    /**
     * <p>
     * If specified, the deployment configuration name can be either one of the predefined configurations provided with
     * AWS CodeDeploy or a custom deployment configuration that you create by calling the create deployment
     * configuration operation.
     * </p>
     * <p>
     * <code>CodeDeployDefault.OneAtATime</code> is the default deployment configuration. It is used if a configuration
     * isn't specified for the deployment or deployment group.
     * </p>
     * <p>
     * For more information about the predefined deployment configurations in AWS CodeDeploy, see <a
     * href="https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-configurations.html">Working with
     * Deployment Configurations in CodeDeploy</a> in the <i>AWS CodeDeploy User Guide</i>.
     * </p>
     */
    private String deploymentConfigName;
    /**
     * <p>
     * A service role Amazon Resource Name (ARN) that allows AWS CodeDeploy to act on the user's behalf when interacting
     * with AWS services.
     * </p>
     */
    private String serviceRoleArn;
    /**
     * <p>
     * Information about the type of deployment, in-place or blue/green, that you want to run and whether to route
     * deployment traffic behind a load balancer.
     * </p>
     */
    private DeploymentStyle deploymentStyle;
    /**
     * <p>
     * Information about blue/green deployment options for a deployment group.
     * </p>
     */
    private BlueGreenDeploymentConfiguration blueGreenDeploymentConfiguration;
    /**
     * <p>
     * Information about the load balancer used in a deployment.
     * </p>
     */
    private LoadBalancerInfo loadBalancerInfo;
    /**
     * <p>
     * The target Amazon ECS services in the deployment group. This applies only to deployment groups that use the
     * Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and service name
     * pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     * </p>
     */
    private com.amazonaws.internal.SdkInternalList<ECSService> ecsServices;
    /**
     * <p>
     * The name of an AWS CodeDeploy application associated with the IAM user or AWS account.
     * </p>
     *
     * @param applicationName
     *        The name of an AWS CodeDeploy application associated with the IAM user or AWS account.
     */

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * <p>
     * The name of an AWS CodeDeploy application associated with the IAM user or AWS account.
     * </p>
     *
     * @return The name of an AWS CodeDeploy application associated with the IAM user or AWS account.
     */

    public String getApplicationName() {
        return this.applicationName;
    }

    /**
     * <p>
     * The name of an AWS CodeDeploy application associated with the IAM user or AWS account.
     * </p>
     *
     * @param applicationName
     *        The name of an AWS CodeDeploy application associated with the IAM user or AWS account.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withApplicationName(String applicationName) {
        setApplicationName(applicationName);
        return this;
    }

    /**
     * <p>
     * The name of a new deployment group for the specified application.
     * </p>
     *
     * @param deploymentGroupName
     *        The name of a new deployment group for the specified application.
     */

    public void setDeploymentGroupName(String deploymentGroupName) {
        this.deploymentGroupName = deploymentGroupName;
    }

    /**
     * <p>
     * The name of a new deployment group for the specified application.
     * </p>
     *
     * @return The name of a new deployment group for the specified application.
     */

    public String getDeploymentGroupName() {
        return this.deploymentGroupName;
    }

    /**
     * <p>
     * The name of a new deployment group for the specified application.
     * </p>
     *
     * @param deploymentGroupName
     *        The name of a new deployment group for the specified application.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withDeploymentGroupName(String deploymentGroupName) {
        setDeploymentGroupName(deploymentGroupName);
        return this;
    }

    /**
     * <p>
     * If specified, the deployment configuration name can be either one of the predefined configurations provided with
     * AWS CodeDeploy or a custom deployment configuration that you create by calling the create deployment
     * configuration operation.
     * </p>
     * <p>
     * <code>CodeDeployDefault.OneAtATime</code> is the default deployment configuration. It is used if a configuration
     * isn't specified for the deployment or deployment group.
     * </p>
     * <p>
     * For more information about the predefined deployment configurations in AWS CodeDeploy, see <a
     * href="https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-configurations.html">Working with
     * Deployment Configurations in CodeDeploy</a> in the <i>AWS CodeDeploy User Guide</i>.
     * </p>
     *
     * @param deploymentConfigName
     *        If specified, the deployment configuration name can be either one of the predefined configurations
     *        provided with AWS CodeDeploy or a custom deployment configuration that you create by calling the create
     *        deployment configuration operation.</p>
     *        <p>
     *        <code>CodeDeployDefault.OneAtATime</code> is the default deployment configuration. It is used if a
     *        configuration isn't specified for the deployment or deployment group.
     *        </p>
     *        <p>
     *        For more information about the predefined deployment configurations in AWS CodeDeploy, see <a
     *        href="https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-configurations.html">Working with
     *        Deployment Configurations in CodeDeploy</a> in the <i>AWS CodeDeploy User Guide</i>.
     */

    public void setDeploymentConfigName(String deploymentConfigName) {
        this.deploymentConfigName = deploymentConfigName;
    }

    /**
     * <p>
     * If specified, the deployment configuration name can be either one of the predefined configurations provided with
     * AWS CodeDeploy or a custom deployment configuration that you create by calling the create deployment
     * configuration operation.
     * </p>
     * <p>
     * <code>CodeDeployDefault.OneAtATime</code> is the default deployment configuration. It is used if a configuration
     * isn't specified for the deployment or deployment group.
     * </p>
     * <p>
     * For more information about the predefined deployment configurations in AWS CodeDeploy, see <a
     * href="https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-configurations.html">Working with
     * Deployment Configurations in CodeDeploy</a> in the <i>AWS CodeDeploy User Guide</i>.
     * </p>
     *
     * @return If specified, the deployment configuration name can be either one of the predefined configurations
     *         provided with AWS CodeDeploy or a custom deployment configuration that you create by calling the create
     *         deployment configuration operation.</p>
     *         <p>
     *         <code>CodeDeployDefault.OneAtATime</code> is the default deployment configuration. It is used if a
     *         configuration isn't specified for the deployment or deployment group.
     *         </p>
     *         <p>
     *         For more information about the predefined deployment configurations in AWS CodeDeploy, see <a
     *         href="https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-configurations.html">Working
     *         with Deployment Configurations in CodeDeploy</a> in the <i>AWS CodeDeploy User Guide</i>.
     */

    public String getDeploymentConfigName() {
        return this.deploymentConfigName;
    }

    /**
     * <p>
     * If specified, the deployment configuration name can be either one of the predefined configurations provided with
     * AWS CodeDeploy or a custom deployment configuration that you create by calling the create deployment
     * configuration operation.
     * </p>
     * <p>
     * <code>CodeDeployDefault.OneAtATime</code> is the default deployment configuration. It is used if a configuration
     * isn't specified for the deployment or deployment group.
     * </p>
     * <p>
     * For more information about the predefined deployment configurations in AWS CodeDeploy, see <a
     * href="https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-configurations.html">Working with
     * Deployment Configurations in CodeDeploy</a> in the <i>AWS CodeDeploy User Guide</i>.
     * </p>
     *
     * @param deploymentConfigName
     *        If specified, the deployment configuration name can be either one of the predefined configurations
     *        provided with AWS CodeDeploy or a custom deployment configuration that you create by calling the create
     *        deployment configuration operation.</p>
     *        <p>
     *        <code>CodeDeployDefault.OneAtATime</code> is the default deployment configuration. It is used if a
     *        configuration isn't specified for the deployment or deployment group.
     *        </p>
     *        <p>
     *        For more information about the predefined deployment configurations in AWS CodeDeploy, see <a
     *        href="https://docs.aws.amazon.com/codedeploy/latest/userguide/deployment-configurations.html">Working with
     *        Deployment Configurations in CodeDeploy</a> in the <i>AWS CodeDeploy User Guide</i>.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withDeploymentConfigName(String deploymentConfigName) {
        setDeploymentConfigName(deploymentConfigName);
        return this;
    }
    /**
     * <p>
     * A service role Amazon Resource Name (ARN) that allows AWS CodeDeploy to act on the user's behalf when interacting
     * with AWS services.
     * </p>
     *
     * @param serviceRoleArn
     *        A service role Amazon Resource Name (ARN) that allows AWS CodeDeploy to act on the user's behalf when
     *        interacting with AWS services.
     */

    public void setServiceRoleArn(String serviceRoleArn) {
        this.serviceRoleArn = serviceRoleArn;
    }

    /**
     * <p>
     * A service role Amazon Resource Name (ARN) that allows AWS CodeDeploy to act on the user's behalf when interacting
     * with AWS services.
     * </p>
     *
     * @return A service role Amazon Resource Name (ARN) that allows AWS CodeDeploy to act on the user's behalf when
     *         interacting with AWS services.
     */

    public String getServiceRoleArn() {
        return this.serviceRoleArn;
    }

    /**
     * <p>
     * A service role Amazon Resource Name (ARN) that allows AWS CodeDeploy to act on the user's behalf when interacting
     * with AWS services.
     * </p>
     *
     * @param serviceRoleArn
     *        A service role Amazon Resource Name (ARN) that allows AWS CodeDeploy to act on the user's behalf when
     *        interacting with AWS services.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withServiceRoleArn(String serviceRoleArn) {
        setServiceRoleArn(serviceRoleArn);
        return this;
    }

    /**
     * <p>
     * Information about the type of deployment, in-place or blue/green, that you want to run and whether to route
     * deployment traffic behind a load balancer.
     * </p>
     *
     * @param deploymentStyle
     *        Information about the type of deployment, in-place or blue/green, that you want to run and whether to
     *        route deployment traffic behind a load balancer.
     */

    public void setDeploymentStyle(DeploymentStyle deploymentStyle) {
        this.deploymentStyle = deploymentStyle;
    }

    /**
     * <p>
     * Information about the type of deployment, in-place or blue/green, that you want to run and whether to route
     * deployment traffic behind a load balancer.
     * </p>
     *
     * @return Information about the type of deployment, in-place or blue/green, that you want to run and whether to
     *         route deployment traffic behind a load balancer.
     */

    public DeploymentStyle getDeploymentStyle() {
        return this.deploymentStyle;
    }

    /**
     * <p>
     * Information about the type of deployment, in-place or blue/green, that you want to run and whether to route
     * deployment traffic behind a load balancer.
     * </p>
     *
     * @param deploymentStyle
     *        Information about the type of deployment, in-place or blue/green, that you want to run and whether to
     *        route deployment traffic behind a load balancer.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withDeploymentStyle(DeploymentStyle deploymentStyle) {
        setDeploymentStyle(deploymentStyle);
        return this;
    }

    /**
     * <p>
     * Information about blue/green deployment options for a deployment group.
     * </p>
     *
     * @param blueGreenDeploymentConfiguration
     *        Information about blue/green deployment options for a deployment group.
     */

    public void setBlueGreenDeploymentConfiguration(BlueGreenDeploymentConfiguration blueGreenDeploymentConfiguration) {
        this.blueGreenDeploymentConfiguration = blueGreenDeploymentConfiguration;
    }

    /**
     * <p>
     * Information about blue/green deployment options for a deployment group.
     * </p>
     *
     * @return Information about blue/green deployment options for a deployment group.
     */

    public BlueGreenDeploymentConfiguration getBlueGreenDeploymentConfiguration() {
        return this.blueGreenDeploymentConfiguration;
    }

    /**
     * <p>
     * Information about blue/green deployment options for a deployment group.
     * </p>
     *
     * @param blueGreenDeploymentConfiguration
     *        Information about blue/green deployment options for a deployment group.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withBlueGreenDeploymentConfiguration(BlueGreenDeploymentConfiguration blueGreenDeploymentConfiguration) {
        setBlueGreenDeploymentConfiguration(blueGreenDeploymentConfiguration);
        return this;
    }

    /**
     * <p>
     * Information about the load balancer used in a deployment.
     * </p>
     *
     * @param loadBalancerInfo
     *        Information about the load balancer used in a deployment.
     */

    public void setLoadBalancerInfo(LoadBalancerInfo loadBalancerInfo) {
        this.loadBalancerInfo = loadBalancerInfo;
    }

    /**
     * <p>
     * Information about the load balancer used in a deployment.
     * </p>
     *
     * @return Information about the load balancer used in a deployment.
     */

    public LoadBalancerInfo getLoadBalancerInfo() {
        return this.loadBalancerInfo;
    }

    /**
     * <p>
     * Information about the load balancer used in a deployment.
     * </p>
     *
     * @param loadBalancerInfo
     *        Information about the load balancer used in a deployment.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withLoadBalancerInfo(LoadBalancerInfo loadBalancerInfo) {
        setLoadBalancerInfo(loadBalancerInfo);
        return this;
    }
    /**
     * <p>
     * The target Amazon ECS services in the deployment group. This applies only to deployment groups that use the
     * Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and service name
     * pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     * </p>
     *
     * @return The target Amazon ECS services in the deployment group. This applies only to deployment groups that use
     *         the Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and
     *         service name pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     */

    public java.util.List<ECSService> getEcsServices() {
        if (ecsServices == null) {
            ecsServices = new com.amazonaws.internal.SdkInternalList<ECSService>();
        }
        return ecsServices;
    }

    /**
     * <p>
     * The target Amazon ECS services in the deployment group. This applies only to deployment groups that use the
     * Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and service name
     * pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     * </p>
     *
     * @param ecsServices
     *        The target Amazon ECS services in the deployment group. This applies only to deployment groups that use
     *        the Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and
     *        service name pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     */

    public void setEcsServices(java.util.Collection<ECSService> ecsServices) {
        if (ecsServices == null) {
            this.ecsServices = null;
            return;
        }

        this.ecsServices = new com.amazonaws.internal.SdkInternalList<ECSService>(ecsServices);
    }

    /**
     * <p>
     * The target Amazon ECS services in the deployment group. This applies only to deployment groups that use the
     * Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and service name
     * pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     * </p>
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setEcsServices(java.util.Collection)} or {@link #withEcsServices(java.util.Collection)} if you want to
     * override the existing values.
     * </p>
     *
     * @param ecsServices
     *        The target Amazon ECS services in the deployment group. This applies only to deployment groups that use
     *        the Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and
     *        service name pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withEcsServices(ECSService... ecsServices) {
        if (this.ecsServices == null) {
            setEcsServices(new com.amazonaws.internal.SdkInternalList<ECSService>(ecsServices.length));
        }
        for (ECSService ele : ecsServices) {
            this.ecsServices.add(ele);
        }
        return this;
    }

    /**
     * <p>
     * The target Amazon ECS services in the deployment group. This applies only to deployment groups that use the
     * Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and service name
     * pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     * </p>
     *
     * @param ecsServices
     *        The target Amazon ECS services in the deployment group. This applies only to deployment groups that use
     *        the Amazon ECS compute platform. A target Amazon ECS service is specified as an Amazon ECS cluster and
     *        service name pair using the format <code>&lt;clustername&gt;:&lt;servicename&gt;</code>.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public CreateDeploymentGroupRequest withEcsServices(java.util.Collection<ECSService> ecsServices) {
        setEcsServices(ecsServices);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationName() != null)
            sb.append("ApplicationName: ").append(getApplicationName()).append(",");
        if (getDeploymentGroupName() != null)
            sb.append("DeploymentGroupName: ").append(getDeploymentGroupName()).append(",");
        if (getDeploymentConfigName() != null)
            sb.append("DeploymentConfigName: ").append(getDeploymentConfigName()).append(",");
        if (getServiceRoleArn() != null)
            sb.append("ServiceRoleArn: ").append(getServiceRoleArn()).append(",");
        if (getDeploymentStyle() != null)
            sb.append("DeploymentStyle: ").append(getDeploymentStyle()).append(",");
        if (getBlueGreenDeploymentConfiguration() != null)
            sb.append("BlueGreenDeploymentConfiguration: ").append(getBlueGreenDeploymentConfiguration()).append(",");
        if (getLoadBalancerInfo() != null)
            sb.append("LoadBalancerInfo: ").append(getLoadBalancerInfo()).append(",");
        if (getEcsServices() != null)
            sb.append("EcsServices: ").append(getEcsServices()).append(",");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof CreateDeploymentGroupRequest == false)
            return false;
        CreateDeploymentGroupRequest other = (CreateDeploymentGroupRequest) obj;
        if (other.getApplicationName() == null ^ this.getApplicationName() == null)
            return false;
        if (other.getApplicationName() != null && other.getApplicationName().equals(this.getApplicationName()) == false)
            return false;
        if (other.getDeploymentGroupName() == null ^ this.getDeploymentGroupName() == null)
            return false;
        if (other.getDeploymentGroupName() != null && other.getDeploymentGroupName().equals(this.getDeploymentGroupName()) == false)
            return false;
        if (other.getDeploymentConfigName() == null ^ this.getDeploymentConfigName() == null)
            return false;
        if (other.getDeploymentConfigName() != null && other.getDeploymentConfigName().equals(this.getDeploymentConfigName()) == false)
            return false;
        if (other.getServiceRoleArn() == null ^ this.getServiceRoleArn() == null)
            return false;
        if (other.getServiceRoleArn() != null && other.getServiceRoleArn().equals(this.getServiceRoleArn()) == false)
            return false;
        if (other.getDeploymentStyle() == null ^ this.getDeploymentStyle() == null)
            return false;
        if (other.getDeploymentStyle() != null && other.getDeploymentStyle().equals(this.getDeploymentStyle()) == false)
            return false;
        if (other.getBlueGreenDeploymentConfiguration() == null ^ this.getBlueGreenDeploymentConfiguration() == null)
            return false;
        if (other.getBlueGreenDeploymentConfiguration() != null
                && other.getBlueGreenDeploymentConfiguration().equals(this.getBlueGreenDeploymentConfiguration()) == false)
            return false;
        if (other.getLoadBalancerInfo() == null ^ this.getLoadBalancerInfo() == null)
            return false;
        if (other.getLoadBalancerInfo() != null && other.getLoadBalancerInfo().equals(this.getLoadBalancerInfo()) == false)
            return false;
        if (other.getEcsServices() == null ^ this.getEcsServices() == null)
            return false;
        if (other.getEcsServices() != null && other.getEcsServices().equals(this.getEcsServices()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getApplicationName() == null) ? 0 : getApplicationName().hashCode());
        hashCode = prime * hashCode + ((getDeploymentGroupName() == null) ? 0 : getDeploymentGroupName().hashCode());
        hashCode = prime * hashCode + ((getDeploymentConfigName() == null) ? 0 : getDeploymentConfigName().hashCode());
        hashCode = prime * hashCode + ((getServiceRoleArn() == null) ? 0 : getServiceRoleArn().hashCode());
        hashCode = prime * hashCode + ((getDeploymentStyle() == null) ? 0 : getDeploymentStyle().hashCode());
        hashCode = prime * hashCode + ((getBlueGreenDeploymentConfiguration() == null) ? 0 : getBlueGreenDeploymentConfiguration().hashCode());
        hashCode = prime * hashCode + ((getLoadBalancerInfo() == null) ? 0 : getLoadBalancerInfo().hashCode());
        hashCode = prime * hashCode + ((getEcsServices() == null) ? 0 : getEcsServices().hashCode());
        return hashCode;
    }
}
