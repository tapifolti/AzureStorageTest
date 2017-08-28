package com.tapifolti.azurestorage;

import com.tapifolti.azurestorage.resources.DownloadResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AzureStorageTestApplication extends Application<AzureStorageTestConfiguration> {

    public static void main(final String[] args) throws Exception {
        new AzureStorageTestApplication().run(args);
    }

    @Override
    public String getName() {
        return "AzureStorageTest";
    }

    @Override
    public void initialize(final Bootstrap<AzureStorageTestConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final AzureStorageTestConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new DownloadResource(configuration.getConnectionString()));
    }

}
