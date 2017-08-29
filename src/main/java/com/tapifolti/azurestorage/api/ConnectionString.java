package com.tapifolti.azurestorage.api;

/**
 * Created by tapifolti on 8/28/2017.
 */
public class ConnectionString {
    private String connectionString = "";
    public ConnectionString() {

    }

    public ConnectionString(boolean isHTTPS, String accountName, String accountKey) {
        if (isHTTPS) {
            connectionString = "DefaultEndpointsProtocol=https;"; // AccountName=tapifolti;AccountKey=OqyzcC1TaEA3BE/d187n3J9cSHPMfU1G+WdtpDC+n8urHQX9BZFPzrnqgwvvSJsPhYZNEMOW7iuS4YTxSlhCOw==;EndpointSuffix=core.windows.net"
        } else {
            connectionString = "DefaultEndpointsProtocol=http;";
        }
        connectionString += "AccountName=" + accountName + ";";
        // TODO accountKey should be stored secure
        connectionString += "AccountKey=" + accountKey + ";";
        connectionString += "EndpointSuffix=core.windows.net";
    }

    public String getConnectionString() {
        return connectionString;
    }
}
