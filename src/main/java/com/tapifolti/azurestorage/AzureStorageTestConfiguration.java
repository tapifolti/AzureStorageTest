package com.tapifolti.azurestorage;

import com.tapifolti.azurestorage.api.ConnectionString;
import com.tapifolti.azurestorage.api.StorageLayout;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import java.util.Map;

public class AzureStorageTestConfiguration extends Configuration {
    @NotNull
    private ConnectionString connectionString = new ConnectionString();

    @NotNull
    private StorageLayout storageLayout = new StorageLayout();

    @JsonProperty("connectionString")
    public ConnectionString getConnectionString() {
        return connectionString;
    }

    @JsonProperty("connectionString")
    public void setConnectionString(Map<String, String> params) {
        connectionString = new ConnectionString(Boolean.parseBoolean(params.get("isHTTTPS")), params.get("accountName"), params.get("accountKey"));
    }

    @JsonProperty("storageLayout")
    public StorageLayout getStorageLayout() {return storageLayout;}

    @JsonProperty("storageLayout")
    public void setStorageLayout(Map<String, String> params) {
        storageLayout = new StorageLayout();
        storageLayout.setRootContainerName(params.get("rootContainerName"));
        storageLayout.setUnpackedContainerName(params.get("unpackedContainerName"));
        storageLayout.setZipExtension(params.get("zipExtension"));
    }
}
