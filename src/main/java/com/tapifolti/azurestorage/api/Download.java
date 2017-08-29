package com.tapifolti.azurestorage.api;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;

/**
 * Created by tapifolti on 8/28/2017.
 */
public class Download {
    public void download(String storageConnectionString, String containerName, File destFilePath) throws Exception {
        CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient serviceClient = account.createCloudBlobClient();

        // Container name must be lower case.
        CloudBlobContainer container = serviceClient.getContainerReference(containerName.toLowerCase());
        container.createIfNotExists();

        CloudBlockBlob blob = container.getBlockBlobReference(destFilePath.getName());

        // Download the image file.
        blob.downloadToFile(destFilePath.getAbsolutePath());

        // catch (FileNotFoundException fileNotFoundException) {
        // catch (StorageException storageException) {
        // catch (Exception e) {
    }
}
