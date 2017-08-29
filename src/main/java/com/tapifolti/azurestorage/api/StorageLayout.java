package com.tapifolti.azurestorage.api;

/**
 * Created by tapifolti on 8/29/2017.
 */
public class StorageLayout {


    private String rootContainerName;
    public void setRootContainerName(String rootContainerName) {
        this.rootContainerName = rootContainerName;
    }
    public String getRootContainerName() {
        return rootContainerName;
    }


    private String unpackedContainerName;
    public void setUnpackedContainerName(String unpackedContainerName) {
        this.unpackedContainerName = unpackedContainerName;
    }
    public String getUnpackedContainerName() {
        return unpackedContainerName;
    }

    private String zipExtension;
    public String getZipExtension() {
        return zipExtension;
    }
    public void setZipExtension(String zipExtension) {
        this.zipExtension = zipExtension;
    }

}
