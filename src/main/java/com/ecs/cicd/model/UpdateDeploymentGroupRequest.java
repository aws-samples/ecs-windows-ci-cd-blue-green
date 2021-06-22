/*
 * Copyright 2016-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 * 
 * http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.ecs.cicd.model;

import java.io.Serializable;

import com.amazonaws.services.codedeploy.model.BlueGreenDeploymentConfiguration;
import com.amazonaws.services.codedeploy.model.DeploymentStyle;
import com.amazonaws.services.codedeploy.model.ECSService;
import com.amazonaws.services.codedeploy.model.LoadBalancerInfo;

public class UpdateDeploymentGroupRequest implements Serializable, Cloneable {

    /**
     * <p>
     * The application name that corresponds to the deployment group to update.
     * </p>
     */
    private String applicationName;
    /**
     * <p>
     * The current name of the deployment group.
     * </p>
     */
    private String currentDeploymentGroupName;
    /**
     * <p>
     * The new name of the deployment group, if you want to change it.
     * </p>
     */
    private String newDeploymentGroupName;
    /**
     * <p>
     * The replacement deployment configuration name to use, if you want to change it.
     * </p>
     */
    private String deploymentConfigName;
    private String serviceRoleArn;
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
    private com.amazonaws.internal.SdkInternalList<ECSService> ecsServices;

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * <p>
     * The application name that corresponds to the deployment group to update.
     * </p>
     * 
     * @return The application name that corresponds to the deployment group to update.
     */

    public String getApplicationName() {
        return this.applicationName;
    }

    /**
     * <p>
     * The application name that corresponds to the deployment group to update.
     * </p>
     * 
     * @param applicationName
     *        The application name that corresponds to the deployment group to update.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public UpdateDeploymentGroupRequest withApplicationName(String applicationName) {
        setApplicationName(applicationName);
        return this;
    }

    /**
     * <p>
     * The current name of the deployment group.
     * </p>
     * 
     * @param currentDeploymentGroupName
     *        The current name of the deployment group.
     */

    public void setCurrentDeploymentGroupName(String currentDeploymentGroupName) {
        this.currentDeploymentGroupName = currentDeploymentGroupName;
    }

    /**
     * <p>
     * The current name of the deployment group.
     * </p>
     * 
     * @return The current name of the deployment group.
     */

    public String getCurrentDeploymentGroupName() {
        return this.currentDeploymentGroupName;
    }

    /**
     * <p>
     * The current name of the deployment group.
     * </p>
     * 
     * @param currentDeploymentGroupName
     *        The current name of the deployment group.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public UpdateDeploymentGroupRequest withCurrentDeploymentGroupName(String currentDeploymentGroupName) {
        setCurrentDeploymentGroupName(currentDeploymentGroupName);
        return this;
    }

    /**
     * <p>
     * The new name of the deployment group, if you want to change it.
     * </p>
     * 
     * @param newDeploymentGroupName
     *        The new name of the deployment group, if you want to change it.
     */

    public void setNewDeploymentGroupName(String newDeploymentGroupName) {
        this.newDeploymentGroupName = newDeploymentGroupName;
    }

    /**
     * <p>
     * The new name of the deployment group, if you want to change it.
     * </p>
     * 
     * @return The new name of the deployment group, if you want to change it.
     */

    public String getNewDeploymentGroupName() {
        return this.newDeploymentGroupName;
    }

    /**
     * <p>
     * The new name of the deployment group, if you want to change it.
     * </p>
     * 
     * @param newDeploymentGroupName
     *        The new name of the deployment group, if you want to change it.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public UpdateDeploymentGroupRequest withNewDeploymentGroupName(String newDeploymentGroupName) {
        setNewDeploymentGroupName(newDeploymentGroupName);
        return this;
    }

    /**
     * <p>
     * The replacement deployment configuration name to use, if you want to change it.
     * </p>
     * 
     * @param deploymentConfigName
     *        The replacement deployment configuration name to use, if you want to change it.
     */

    public void setDeploymentConfigName(String deploymentConfigName) {
        this.deploymentConfigName = deploymentConfigName;
    }

    /**
     * <p>
     * The replacement deployment configuration name to use, if you want to change it.
     * </p>
     * 
     * @return The replacement deployment configuration name to use, if you want to change it.
     */

    public String getDeploymentConfigName() {
        return this.deploymentConfigName;
    }

    /**
     * <p>
     * The replacement deployment configuration name to use, if you want to change it.
     * </p>
     * 
     * @param deploymentConfigName
     *        The replacement deployment configuration name to use, if you want to change it.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public UpdateDeploymentGroupRequest withDeploymentConfigName(String deploymentConfigName) {
        setDeploymentConfigName(deploymentConfigName);
        return this;
    }

    /**
     * <p>
     * A replacement ARN for the service role, if you want to change it.
     * </p>
     * 
     * @param serviceRoleArn
     *        A replacement ARN for the service role, if you want to change it.
     */

    public void setServiceRoleArn(String serviceRoleArn) {
        this.serviceRoleArn = serviceRoleArn;
    }

    /**
     * <p>
     * A replacement ARN for the service role, if you want to change it.
     * </p>
     * 
     * @return A replacement ARN for the service role, if you want to change it.
     */

    public String getServiceRoleArn() {
        return this.serviceRoleArn;
    }

    /**
     * <p>
     * A replacement ARN for the service role, if you want to change it.
     * </p>
     * 
     * @param serviceRoleArn
     *        A replacement ARN for the service role, if you want to change it.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public UpdateDeploymentGroupRequest withServiceRoleArn(String serviceRoleArn) {
        setServiceRoleArn(serviceRoleArn);
        return this;
    }

    /**
     * <p>
     * Information about the type of deployment, either in-place or blue/green, you want to run and whether to route
     * deployment traffic behind a load balancer.
     * </p>
     * 
     * @param deploymentStyle
     *        Information about the type of deployment, either in-place or blue/green, you want to run and whether to
     *        route deployment traffic behind a load balancer.
     */

    public void setDeploymentStyle(DeploymentStyle deploymentStyle) {
        this.deploymentStyle = deploymentStyle;
    }

    /**
     * <p>
     * Information about the type of deployment, either in-place or blue/green, you want to run and whether to route
     * deployment traffic behind a load balancer.
     * </p>
     * 
     * @return Information about the type of deployment, either in-place or blue/green, you want to run and whether to
     *         route deployment traffic behind a load balancer.
     */

    public DeploymentStyle getDeploymentStyle() {
        return this.deploymentStyle;
    }

    /**
     * <p>
     * Information about the type of deployment, either in-place or blue/green, you want to run and whether to route
     * deployment traffic behind a load balancer.
     * </p>
     * 
     * @param deploymentStyle
     *        Information about the type of deployment, either in-place or blue/green, you want to run and whether to
     *        route deployment traffic behind a load balancer.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public UpdateDeploymentGroupRequest withDeploymentStyle(DeploymentStyle deploymentStyle) {
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

    public UpdateDeploymentGroupRequest withBlueGreenDeploymentConfiguration(BlueGreenDeploymentConfiguration blueGreenDeploymentConfiguration) {
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

    public UpdateDeploymentGroupRequest withLoadBalancerInfo(LoadBalancerInfo loadBalancerInfo) {
        setLoadBalancerInfo(loadBalancerInfo);
        return this;
    }


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

    public UpdateDeploymentGroupRequest withEcsServices(ECSService... ecsServices) {
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

    public UpdateDeploymentGroupRequest withEcsServices(java.util.Collection<ECSService> ecsServices) {
        setEcsServices(ecsServices);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationName() != null)
            sb.append("ApplicationName: ").append(getApplicationName()).append(",");
        if (getCurrentDeploymentGroupName() != null)
            sb.append("CurrentDeploymentGroupName: ").append(getCurrentDeploymentGroupName()).append(",");
        if (getNewDeploymentGroupName() != null)
            sb.append("NewDeploymentGroupName: ").append(getNewDeploymentGroupName()).append(",");
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

        if (obj instanceof UpdateDeploymentGroupRequest == false)
            return false;
        UpdateDeploymentGroupRequest other = (UpdateDeploymentGroupRequest) obj;
        if (other.getApplicationName() == null ^ this.getApplicationName() == null)
            return false;
        if (other.getApplicationName() != null && other.getApplicationName().equals(this.getApplicationName()) == false)
            return false;
        if (other.getCurrentDeploymentGroupName() == null ^ this.getCurrentDeploymentGroupName() == null)
            return false;
        if (other.getCurrentDeploymentGroupName() != null && other.getCurrentDeploymentGroupName().equals(this.getCurrentDeploymentGroupName()) == false)
            return false;
        if (other.getNewDeploymentGroupName() == null ^ this.getNewDeploymentGroupName() == null)
            return false;
        if (other.getNewDeploymentGroupName() != null && other.getNewDeploymentGroupName().equals(this.getNewDeploymentGroupName()) == false)
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
        hashCode = prime * hashCode + ((getCurrentDeploymentGroupName() == null) ? 0 : getCurrentDeploymentGroupName().hashCode());
        hashCode = prime * hashCode + ((getNewDeploymentGroupName() == null) ? 0 : getNewDeploymentGroupName().hashCode());
        hashCode = prime * hashCode + ((getDeploymentConfigName() == null) ? 0 : getDeploymentConfigName().hashCode());
        hashCode = prime * hashCode + ((getServiceRoleArn() == null) ? 0 : getServiceRoleArn().hashCode());
        hashCode = prime * hashCode + ((getDeploymentStyle() == null) ? 0 : getDeploymentStyle().hashCode());
        hashCode = prime * hashCode + ((getBlueGreenDeploymentConfiguration() == null) ? 0 : getBlueGreenDeploymentConfiguration().hashCode());
        hashCode = prime * hashCode + ((getLoadBalancerInfo() == null) ? 0 : getLoadBalancerInfo().hashCode());
        hashCode = prime * hashCode + ((getEcsServices() == null) ? 0 : getEcsServices().hashCode());
        return hashCode;
    }
}
