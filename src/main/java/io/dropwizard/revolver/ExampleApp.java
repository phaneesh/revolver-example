package io.dropwizard.revolver;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.curator.framework.CuratorFramework;

/**
 * Hello world!
 */
public class ExampleApp extends Application<ExampleAppConfiguration> {

    public static void main(String[] args) throws Exception {
        ExampleApp exampleApp = new ExampleApp();
        exampleApp.run(args);
    }

    @Override
    public void initialize(Bootstrap<ExampleAppConfiguration> bootstrap) {
        bootstrap.addBundle(new RevolverBundle<ExampleAppConfiguration>() {

            @Override
            public CuratorFramework getCurator() {
                return null;
            }

            @Override
            public io.dropwizard.revolver.core.config.RevolverConfig getRevolverConfig(ExampleAppConfiguration apiConfiguration) {
                return apiConfiguration.getRevolver();
            }
        });
    }

    @Override
    public void run(ExampleAppConfiguration exampleAppConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new ExampleResource());
    }
}
