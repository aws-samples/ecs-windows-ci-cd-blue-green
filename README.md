# Welcome to ecs-windows-ci-cd-blue-green project!

This project sets up a Windows ECS Cluster with Fully automated Blue/Green deployment powered by AWS Code Deploy. All you 
need to pass is your ECR repo name where the image resides in [cdk.json](cdk.json) via `imageRepository` property.

It is assumed that you already have a mechanism in place to build and push your container image to ECR.
If you don't have the mechanism in place, Pipeline can easily be extended to introduce a code build stage to
build and deploy your container image. As part of demo setup, we have pushed [microsoft/iss](https://hub.docker.com/_/microsoft-windows-servercore-iis?tab=description) image from docker hub to ECR as per instructions [here](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/windows_ecr.html).

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

```
    npm install
```

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!
