package com.tapifolti.azurestorage.api;

import com.tapifolti.azurestorage.resources.UnpackResource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by tapifolti on 9/2/2017.
 */
public class FilesFromZipTest {
    final static Logger log = LoggerFactory.getLogger(FilesFromZipTest.class);

    @Test
    public void extractAllTest() {

        // File fromZip = new File("L:\\Users\\anya\\IdeaProjects\\AzureStorage\\azurestorage\\temp\\testproject.7z");
        File fromZip = new File("L:\\Users\\anya\\IdeaProjects\\AzureStorage\\azurestorage\\temp\\2017_07_14__16_15_19.7z");
        File toDirectory = new File("L:\\Users\\anya\\IdeaProjects\\AzureStorage\\azurestorage\\temp\\testproject");
        try {

            new FilesFromZip().extractAll(fromZip, toDirectory);

        } catch(Exception ex) {
            log.error("zip failed", ex);
        }

    }
}
