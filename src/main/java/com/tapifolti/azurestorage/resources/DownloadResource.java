package com.tapifolti.azurestorage.resources;

import com.codahale.metrics.annotation.Timed;
import com.tapifolti.azurestorage.api.ConnectionString;
import com.tapifolti.azurestorage.api.Download;
import com.tapifolti.azurestorage.api.StorageLayout;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.NonEmptyStringParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by tapifolti on 8/28/2017.
 */

@Path("/download")
public class DownloadResource {
    final static Logger log = LoggerFactory.getLogger(DownloadResource.class);

    private String connectionString;
    private StorageLayout storageLayout;

    public DownloadResource(ConnectionString connectionString, StorageLayout storageLayout) {
        this.connectionString = connectionString.getConnectionString();
        this.storageLayout = storageLayout;
    }

    private Response sendFile(String project, String fileName) {
        try {
            byte[] blobData = new Download().downloadByteArray(connectionString, storageLayout.getRootContainerName(),
                    storageLayout.getUnpackedContainerName() + "/" + project+ "/" + fileName);
            return Response.ok(blobData).build();
        } catch (Exception ex ) {
            log.error("Downalod error", ex);
            String result = "Downalod error\n" + ex.getMessage();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
        }
    }

    // TODO return HTML and add download duration info
    // See: https://ben.lobaugh.net/blog/33713/using-binary-image-data-to-display-an-image-in-html
    @GET
    @Produces("image/png")
    @Path("front/{project}")
    @Timed
    public Response front(@PathParam("project") NonEmptyStringParam project) {
//        BufferedImage image = ...;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(image, "png", baos);
//        byte[] imageData = baos.toByteArray();
        // uncomment line below to send streamed
        // return Response.ok(new ByteArrayInputStream(imageData)).build();

        return sendFile(project.get().get(), "front.png");
    }

    @GET
    @Produces("image/png")
    @Path("rear/{project}")
    @Timed
    public Response rear(@PathParam("project") NonEmptyStringParam project) {
        return sendFile(project.get().get(), "rear.png");
    }

}
