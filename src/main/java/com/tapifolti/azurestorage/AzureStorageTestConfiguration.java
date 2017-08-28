package com.tapifolti.azurestorage;

import com.tapifolti.azurestorage.api.ConnectionString;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import java.util.Map;

public class AzureStorageTestConfiguration extends Configuration {
    @NotNull
    private ConnectionString connectionString = null;

    @JsonProperty("connectionString")
    public ConnectionString getConnectionString() {
        return connectionString;
    }

    @JsonProperty("connectionString")
    public void setConnectionString(Map<String, String> params) {
        ConnectionString connectionString = new ConnectionString(Boolean.parseBoolean(params.get("isHTTTPS")), params.get("accountName"), params.get("accountKey"));
    }
}
