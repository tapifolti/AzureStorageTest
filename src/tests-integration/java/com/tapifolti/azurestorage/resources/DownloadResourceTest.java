package com.tapifolti.azurestorage.resources;

import com.tapifolti.azurestorage.api.ConnectionString;
import com.tapifolti.azurestorage.api.StorageLayout;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by tapifolti on 9/4/2017.
 */
public class DownloadResourceTest {
    final static Logger log = LoggerFactory.getLogger(DownloadResourceTest.class);
    private static ConnectionString connectionString = new ConnectionString(true, "tapifolti", "OqyzcC1TaEA3BE/d187n3J9cSHPMfU1G+WdtpDC+n8urHQX9BZFPzrnqgwvvSJsPhYZNEMOW7iuS4YTxSlhCOw==");

    private static StorageLayout storageLayout = new StorageLayout();

    static {
        storageLayout.setUnpackedContainerName("unpacked");
        storageLayout.setRootContainerName("projects");
        storageLayout.setZipExtension("7z");
    }

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new DownloadResource(connectionString, storageLayout))
            .build();

    @Test
    public void testFront() {
        int status = Response.Status.OK.getStatusCode();
        try {
            status = resources.target("/download/front/testproject").request().get().getStatus();
        } catch (Exception ex ) {
            log.error("Error thrown: ", ex);
        }
        assertTrue(status == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testRear() {
        int status = Response.Status.OK.getStatusCode();
        try {
            status = resources.target("/download/rear/testproject").request().get().getStatus();
        } catch (Exception ex ) {
            log.error("Error thrown: ", ex);
        }
        assertTrue(status == Response.Status.OK.getStatusCode());
    }

    @Test
    public void testFail() {
        int status = Response.Status.OK.getStatusCode();
        try {
            status = resources.target("/download/rear/xxxxxx").request().get().getStatus();
        } catch (Exception ex ) {
            log.error("Error thrown: ", ex);
        }
        assertTrue(status == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
