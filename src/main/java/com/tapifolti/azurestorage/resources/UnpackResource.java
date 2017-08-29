package com.tapifolti.azurestorage.resources;

import com.codahale.metrics.annotation.Timed;
import com.tapifolti.azurestorage.api.ConnectionString;
import com.tapifolti.azurestorage.api.Download;
import com.tapifolti.azurestorage.api.FilesFromZip;
import com.tapifolti.azurestorage.api.StorageLayout;
import io.dropwizard.jersey.params.NonEmptyStringParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

/**
 * Created by tapifolti on 8/29/2017.
 */

@Path("/unpack")
public class UnpackResource {
    final static Logger log = LoggerFactory.getLogger(UnpackResource.class);

    private String connectionString;
    private StorageLayout storageLayout;

    public UnpackResource(ConnectionString connectionString, StorageLayout storageLayout) {
        this.connectionString = connectionString.getConnectionString();
        this.storageLayout = storageLayout;
    }

    // TODO make it async
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("project/{name}")
    @Timed
    public Response unpack(@PathParam("id") NonEmptyStringParam name) {
        String result = "Done";

        // Downaload
        File destZipFilePath = new File("temp/" + name + "." + storageLayout.getZipExtension());
        try {
            new Download().download(connectionString, storageLayout.getRootContainerName(), destZipFilePath);
        } catch (Exception ex) {
            log.error("Downalod error", ex);
            result = ex.getMessage();
        }

        // Unzip
        File toDirectory = new File(destZipFilePath.toPath().toString()+".unpack");
        try {
            new FilesFromZip().extractAll(destZipFilePath, toDirectory);
        } catch (Exception ex) {
            log.error("Unzip error", ex);
            result = ex.getMessage();
        }

        // TODO upload all files to a new sub-container

        // send response
        return Response.ok().entity(result).build();

    }

}
