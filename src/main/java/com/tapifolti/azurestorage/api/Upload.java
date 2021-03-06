package com.tapifolti.azurestorage.api;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by tapifolti on 8/28/2017.
 */
public class Upload {

    public void upload(String storageConnectionString, String containerName, String blolbName, File fileToUpload) throws Exception {
        CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
        CloudBlobClient serviceClient = account.createCloudBlobClient();

        // Container name must be lower case.
        CloudBlobContainer container = serviceClient.getContainerReference(containerName.toLowerCase());
        container.createIfNotExists();

        // Upload an image file.
        CloudBlockBlob blob = container.getBlockBlobReference(blolbName);
        try (BufferedInputStream fis = new BufferedInputStream(new FileInputStream(fileToUpload))) {
            blob.upload(fis, fileToUpload.length());
        }

        // catch (FileNotFoundException fileNotFoundException) {
        // catch (StorageException storageException) {
        // catch (Exception e) {
    }

}
