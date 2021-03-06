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
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created by tapifolti on 9/2/2017.
 */
public class UnpackResourceTest {

    final static Logger log = LoggerFactory.getLogger(UnpackResourceTest.class);
    private static ConnectionString connectionString = new ConnectionString(true, "tapifolti", "OqyzcC1TaEA3BE/d187n3J9cSHPMfU1G+WdtpDC+n8urHQX9BZFPzrnqgwvvSJsPhYZNEMOW7iuS4YTxSlhCOw==");

    private static StorageLayout storageLayout = new StorageLayout();

    static {
        storageLayout.setUnpackedContainerName("unpacked");
        storageLayout.setRootContainerName("projects");
        storageLayout.setZipExtension("7z");
    }

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UnpackResource(connectionString, storageLayout))
            .build();

    @Test
    public void testUnpackResource() {
        Response response = resources.target("/unpack/project/testproject").request().get(); // testproject, 2017_07_14__16_15_19
        String responseStr = (String)response.readEntity(String.class);
        log.info(responseStr);
        assertTrue(response.getStatus() == Response.Status.OK.getStatusCode());
        assertTrue(responseStr.contains("Done"));
    }

    @Test
    public void testUnpackResourceError() {
        int status = Response.Status.OK.getStatusCode();
        try {
            Response response = resources.target("/unpack/project/xxxxxx").request().get();
            String responseStr = (String)response.readEntity(String.class);
            log.info(responseStr);
            status = response.getStatus();
        } catch (Exception ex ) {
            log.error("Error thrown: ", ex);
        }
        assertTrue(status == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}
