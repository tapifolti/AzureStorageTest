package com.tapifolti.azurestorage;

import com.tapifolti.azurestorage.api.ConnectionString;
import com.tapifolti.azurestorage.resources.DownloadResource;
import com.tapifolti.azurestorage.resources.UnpackResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class AzureStorageTestApplication extends Application<AzureStorageTestConfiguration> {

    public static class DependencyInjection extends AbstractBinder {
        private AzureStorageTestConfiguration configuration;
        public DependencyInjection(AzureStorageTestConfiguration configuration) {
            this.configuration = configuration;
        }
        @Override
        protected void configure() {
            ConnectionString connectionString = configuration.getConnectionString();
            bind(connectionString).to(ConnectionString.class);
        }
    }

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
        environment.jersey().register(new DependencyInjection(configuration));
        environment.jersey().register(new DownloadResource(configuration.getConnectionString()));
        environment.jersey().register(new UnpackResource(configuration.getConnectionString(), configuration.getStorageLayout()));
    }

}
