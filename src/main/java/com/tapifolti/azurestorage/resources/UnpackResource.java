package com.tapifolti.azurestorage.resources;

import com.codahale.metrics.annotation.Timed;
import com.tapifolti.azurestorage.api.*;
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
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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

    private static class UploadVisitor extends SimpleFileVisitor<java.nio.file.Path> {
        private String storageConnectionString;
        private String containerName;

        public UploadVisitor(String storageConnectionString, String containerName) {
            this.storageConnectionString = storageConnectionString;
            this.containerName = containerName;
        }

        @Override
        public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attrs) throws IOException
        {
            File fileToUpload = file.toFile();
            try {
                // TODO manage subfolders
                new Upload().upload(storageConnectionString, containerName, fileToUpload);
                Files.deleteIfExists(file);
            } catch (Exception ex) {
                throw new IOException(ex);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException e)
                throws IOException {
            if (e == null) {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            } else {
                throw e;
            }
        }
    }

    // TODO make it async
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("project/{name}")
    @Timed
    public Response unpack(@PathParam("id") NonEmptyStringParam name) {
        String result = "Done";

        String destTempFolderPath = "temp_" + new Long(System.currentTimeMillis()).toString();
        File destTempFolder = new File(destTempFolderPath);
        File destZipFilePath = new File(destTempFolderPath + "/" + name + "." + storageLayout.getZipExtension());
        File toDirectory = new File(destZipFilePath.toPath().toString() + ".unpack");
        try {
            // Downaload
            try {
                destTempFolder.mkdir();
                new Download().download(connectionString, storageLayout.getRootContainerName(), destZipFilePath);
            } catch (Exception ex) {
                log.error("Downalod error", ex);
                result = ex.getMessage();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            // Unzip
            try {
                toDirectory.mkdir();
                new FilesFromZip().extractAll(destZipFilePath, toDirectory);
            } catch (Exception ex) {
                log.error("Unzip error", ex);
                result = ex.getMessage();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            // TODO upload all files to a new sub-container
            try {
                UploadVisitor visitor = new UploadVisitor(connectionString, storageLayout.getRootContainerName());
                Files.walkFileTree(toDirectory.toPath(), visitor);
            } catch (Exception ex) {
                log.error("Upload error", ex);
                result = ex.getMessage();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }


            // send OK response
            return Response.ok().entity(result).build();
        } finally {
            // Cleanup
            toDirectory.delete();
            destTempFolder.delete();

        }
    }

}
