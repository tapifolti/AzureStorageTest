package com.tapifolti.azurestorage.resources;

import com.tapifolti.azurestorage.api.ConnectionString;
import com.tapifolti.azurestorage.api.StorageLayout;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

/**
 * Created by tapifolti on 9/3/2017.
 */
public class UploadVisitorTest {
    final static Logger log = LoggerFactory.getLogger(UploadVisitorTest.class);
    private static ConnectionString connectionString = new ConnectionString(true, "tapifolti",
            "OqyzcC1TaEA3BE/d187n3J9cSHPMfU1G+WdtpDC+n8urHQX9BZFPzrnqgwvvSJsPhYZNEMOW7iuS4YTxSlhCOw==");

    private static StorageLayout storageLayout = new StorageLayout();

    static {
        storageLayout.setUnpackedContainerName("unpacked");
        storageLayout.setRootContainerName("projects");
        storageLayout.setZipExtension("7z");
    }

    @Test
    public void visitFileTest() {

        String fromDicrectory = "temp";
        UnpackResource.UploadVisitor uploadVisitor = new UnpackResource.UploadVisitor(connectionString.getConnectionString(),
            storageLayout, new File(fromDicrectory));
        try {
            Files.walkFileTree(new File(fromDicrectory).toPath(), uploadVisitor);
        } catch (IOException iex) {
            log.error("Upload visitor error", iex);
            assertTrue(false);
        }
        log.info("Upload visitor successfully finished");

    }

}
