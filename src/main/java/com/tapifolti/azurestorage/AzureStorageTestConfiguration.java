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
    private ConnectionString connectionString = null;

    @NotNull
    private StorageLayout storageLayout = null;

    @JsonProperty("connectionString")
    public ConnectionString getConnectionString() {
        return connectionString;
    }

    @JsonProperty("connectionString")
    public void setConnectionString(Map<String, String> params) {
        ConnectionString connectionString = new ConnectionString(Boolean.parseBoolean(params.get("isHTTTPS")), params.get("accountName"), params.get("accountKey"));
    }

    @JsonProperty("storageLayout")
    public StorageLayout getStorageLayout() {return storageLayout;}

    @JsonProperty("storageLayout")
    public void setStorageLayout(Map<String, String> params) {
        StorageLayout storageLayout = new StorageLayout();
        storageLayout.setRootContainerName(params.get("rootContainerName"));
        storageLayout.setUnpackedContainerName(params.get("unpackedContainerName"));
        storageLayout.setZipExtension(params.get("zipExtension"));
    }
}
