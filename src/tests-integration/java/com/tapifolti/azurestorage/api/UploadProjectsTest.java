package com.tapifolti.azurestorage.api;

/**
 * Created by tapifolti on 8/28/2017.
 */

import java.io.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.assertTrue;

public class UploadProjectsTest {
    final static Logger log = LoggerFactory.getLogger(UploadProjectsTest.class);

    ConnectionString connectionString = new ConnectionString(true, "tapifolti", "OqyzcC1TaEA3BE/d187n3J9cSHPMfU1G+WdtpDC+n8urHQX9BZFPzrnqgwvvSJsPhYZNEMOW7iuS4YTxSlhCOw==");

    private static final String CONTAINER_NAME = "projects";

    @Test
    public void uploadTest() {
        Upload loader = new Upload();
        File toUpload = new File("L:\\Zsuzsa\\PeterProject\\PeterAdat\\projects\\2017_07_14__16_15_19.7z"); // testproject
        try {
            loader.upload(connectionString.getConnectionString(), CONTAINER_NAME, toUpload.getName(), toUpload);
            assertTrue(true);
        } catch (Exception ex) {
            log.error("Upload error", ex);
            assertTrue(false);
        }
        log.info("Upload successfully finished");
    }

}
