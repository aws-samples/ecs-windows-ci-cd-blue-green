package com.ecs.cicd;

import java.io.IOException;

import software.amazon.awscdk.core.App;
import software.amazon.awscdk.core.StackProps;

public class EcsWindowsCiCdBlueGreenApp {
    public static void main(final String[] args) throws IOException {
        App app = new App();

        new EcsWindowsBlueGreenStack(app, "EcsWindowsBlueGreenClusterStack", StackProps.builder()
                .build());

        new PipelineEcsWindowsBlueGreenStack(app, "PipelineEcsWindowsBlueGreenStack", StackProps.builder()
                .build());

        app.synth();
    }
}
