package com.tapifolti.azurestorage.resources;

import com.codahale.metrics.annotation.Timed;
import com.tapifolti.azurestorage.api.ConnectionString;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.NonEmptyStringParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by tapifolti on 8/28/2017.
 */

@Path("/download")
public class DownloadResource {

    private String connectionString;
    public DownloadResource(ConnectionString connectionString) {
        this.connectionString = connectionString.getConnectionString();
    }

    @GET
    @Produces("image/png")
    @Path("front/{project}")
    @Timed
    public Response front(@PathParam("project") NonEmptyStringParam project) {
        // TODO
//        BufferedImage image = ...;
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(image, "png", baos);
//        byte[] imageData = baos.toByteArray();
//
//        // uncomment line below to send non-streamed
//        // return Response.ok(imageData).build();
//
//        // uncomment line below to send streamed
//        // return Response.ok(new ByteArrayInputStream(imageData)).build();
        return Response.ok().entity("Not yet implemented").build();
    }

    @GET
    @Produces("image/png")
    @Path("rear/{project}")
    @Timed
    public Response rear(@PathParam("project") NonEmptyStringParam project) {
        // TODO
        return Response.ok().entity("Not yet implemented").build();
    }

}
