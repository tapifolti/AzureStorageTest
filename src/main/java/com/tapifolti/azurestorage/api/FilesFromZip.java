package com.tapifolti.azurestorage.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by tapifolti on 8/29/2017.
 */
public class FilesFromZip {

//    public void extractAll(URI fromZip, Path toDirectory) throws IOException {
//        FileSystems.newFileSystem(fromZip, Collections.emptyMap())
//                .getRootDirectories()
//                .forEach(root -> {
//                    try {
//                        Files.walk(root).forEach(path -> {
//                            try {
//                                Files.copy(path, toDirectory);
//                            } catch (IOException iex) {}
//                        });
//                    } catch (IOException iex) {}
//                });
//    }

    public void extractAll(File fromZip, File toDirectory) throws IOException {
        ZipFile zipFile = new ZipFile(fromZip.getAbsolutePath());

        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        // TODO create toDirectory
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            InputStream stream = zipFile.getInputStream(entry);
            // TODO save file into toDirectory
        }
    }
}

