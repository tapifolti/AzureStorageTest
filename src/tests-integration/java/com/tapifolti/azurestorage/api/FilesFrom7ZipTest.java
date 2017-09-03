package com.tapifolti.azurestorage.api;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.assertTrue;

/**
 * Created by tapifolti on 9/2/2017.
 */
public class FilesFrom7ZipTest {
    final static Logger log = LoggerFactory.getLogger(FilesFrom7ZipTest.class);

    @Test
    public void extractAllTest() {

        // File fromZip = new File("L:\\Users\\anya\\IdeaProjects\\AzureStorage\\azurestorage\\temp\\testproject.7z");
        File fromZip = new File("L:\\Users\\anya\\IdeaProjects\\AzureStorage\\azurestorage\\temp\\testproject.7z");
        File toDirectory = new File("L:\\Users\\anya\\IdeaProjects\\AzureStorage\\azurestorage\\temp");
        try {
            new FilesFrom7Zip().extractAll(fromZip, toDirectory);
        } catch(Exception ex) {
            log.error("Unzip failed", ex);
            assertTrue(false);
        }
        log.info("Unzip successfully finished");
    }
}
