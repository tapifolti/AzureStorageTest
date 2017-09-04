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

    private static class DeleteVisitor extends SimpleFileVisitor<java.nio.file.Path> {
        @Override
        public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attrs) throws IOException {
            file.toFile().delete();
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException e) throws IOException {
            if (e == null) {
                dir.toFile().delete();
                return FileVisitResult.CONTINUE;
            } else {
                // if api was not succ, tree-walk is stopped, dir is not cleaned up
                throw e;
            }
        }
    }

    public static class UploadVisitor extends SimpleFileVisitor<java.nio.file.Path> {
        private String storageConnectionString;
        private StorageLayout storageLayout;
        private java.nio.file.Path fromDirectory;

        public UploadVisitor(String storageConnectionString, StorageLayout storageLayout, File fromDirectory) {
            this.storageConnectionString = storageConnectionString;
            this.storageLayout = storageLayout;
            this.fromDirectory = fromDirectory.toPath();
        }

        @Override
        public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attrs) throws IOException {
            File fileToUpload = file.toFile();
            java.nio.file.Path relative = fromDirectory.relativize(file);
            String relativeFile = relative.toString().replace(java.io.File.separator, "/");
            try {
                // manage blolb's subfolders
                new Upload().upload(storageConnectionString, storageLayout.getRootContainerName(),
                        storageLayout.getUnpackedContainerName() + "/" + relativeFile, fileToUpload);
            } catch (Exception ex) {
                throw new IOException(ex);
            } finally {
                fileToUpload.delete();
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(java.nio.file.Path dir, IOException e) throws IOException {
            if (e == null) {
                dir.toFile().delete();
                return FileVisitResult.CONTINUE;
            } else {
                // if api was not succ, tree-walk is stopped, dir is not cleaned up
                throw e;
            }
        }
    }

    private static class Measure {

        public long downloadMsec = 0;
        public long unzipMsec = 0;
        public long uploadMsec = 0;
    }

    private String wrapToHTML(String msg, Measure measure) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "<p>MM</p>\n" +
                "<p/>\n" +
                "<p/>\n" +
                "<p>MS</p>\n" +
                "</body>\n" +
                "</html>";
        if (measure != null) {
            String measureStr = "Download: " + Long.toString(measure.downloadMsec) + " msec<br>" +
                                "Unzip: " + Long.toString(measure.unzipMsec) + " msec<br>" +
                                "Upload: " + Long.toString(measure.uploadMsec) + " msec";
            html = html.replace("MM", measureStr);
        }
        if (msg != null && !msg.isEmpty()) {
            String msgStr = msg.replace("\n", "<br>");
            html = html.replace("MS", msgStr);
        }
        return html;
    }

    // TODO make it async
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("project/{name}")
    @Timed
    public Response unpack(@PathParam("name") NonEmptyStringParam name) {
        String result = "Done";

        String destTempFolderPath = "temp_" + new Long(System.currentTimeMillis()).toString();
        File destTempFolder = new File(destTempFolderPath);
        File destZipFilePath = new File(destTempFolderPath, name.get().get() + "." + storageLayout.getZipExtension());
        Measure measure = new Measure();
        try {
            // Downaload
            try {
                destTempFolder.mkdir();
                long timestart = System.currentTimeMillis();
                new Download().downloadFile(connectionString, storageLayout.getRootContainerName(), destZipFilePath);
                measure.downloadMsec = System.currentTimeMillis() - timestart;
            } catch (Exception ex) {
                log.error("Downalod error", ex);
                result = "Downalod error\n" + ex.getMessage();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(wrapToHTML(result, measure)).build();
            }

            // Unzip
            try {
                long timestart = System.currentTimeMillis();
                new FilesFrom7Zip().extractAll(destZipFilePath, destTempFolder);
                measure.unzipMsec = System.currentTimeMillis() - timestart;
                destZipFilePath.delete();
            } catch (Exception ex) {
                log.error("Unzip error", ex);
                result = "Unzip error\n" + ex.getMessage();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(wrapToHTML(result, measure)).build();
            }

            // Upload all files to the container with folders in blob's name
            try {
                UploadVisitor visitor = new UploadVisitor(connectionString, storageLayout, destTempFolder);
                long timestart = System.currentTimeMillis();
                Files.walkFileTree(destTempFolder.toPath(), visitor);
                measure.uploadMsec = System.currentTimeMillis() - timestart;
            } catch (Exception ex) {
                log.error("Upload error", ex);
                result = "Upload error\n" + ex.getMessage();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(wrapToHTML(result, measure)).build();
            }

            // send OK response
            return Response.ok().entity(wrapToHTML(result, measure)).build();
        } finally {
            // Cleanup
            try {
                if (destTempFolder.exists()) {
                    DeleteVisitor deleter = new DeleteVisitor();
                    Files.walkFileTree(destTempFolder.toPath(), deleter);
                    destTempFolder.delete();
                }
            } catch (IOException iex) {
                log.error("Cleanup error", iex);
            }

        }
    }

}
