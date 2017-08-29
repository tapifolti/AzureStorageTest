package com.tapifolti.azurestorage.upload;

/**
 * Created by tapifolti on 8/28/2017.
 */

import java.io.*;

import com.tapifolti.azurestorage.api.ConnectionString;
import com.tapifolti.azurestorage.api.Upload;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static junit.framework.TestCase.assertTrue;

public class UploadProjectsTest {
    final static Logger log = LoggerFactory.getLogger(UploadProjectsTest.class);

    ConnectionString connectionString = new ConnectionString(true, "tapifolti", "OqyzcC1TaEA3BE/d187n3J9cSHPMfU1G+WdtpDC+n8urHQX9BZFPzrnqgwvvSJsPhYZNEMOW7iuS4YTxSlhCOw==");

    private static final String CONTAINER_NAME = "projects";

    @Test
    public void uploadTest() {
        Upload loader = new Upload();
        File toUpload = new File("L:\\Zsuzsa\\PeterProject\\PeterAdat\\projects\\2017_07_14__14_26_44.7z");
        try {
            loader.upload(connectionString.getConnectionString(), CONTAINER_NAME, toUpload);
            assertTrue(true);
        } catch (Exception ex) {
            log.error("Upload error", ex);
            assertTrue(false);
        }
    }

}
