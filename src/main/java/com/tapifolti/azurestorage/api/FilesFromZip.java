package com.tapifolti.azurestorage.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by tapifolti on 8/29/2017.
 */
public class FilesFromZip {

    private static byte[] bytes = new byte[1024*100];

    public void extractAll(File fromZip, File toDirectory) throws IOException {
        toDirectory.mkdir();
        ZipFile zipFile = new ZipFile(fromZip.getAbsolutePath());
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                continue;
            }
            String name = entry.getName();
            // TODO also create folder referrred in 'name'
            File file = new File(name);
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            // TODO save file into toDirectory + subfolder
            InputStream is = zipFile.getInputStream(entry);
            FileOutputStream fos = new FileOutputStream(file);
            int length;
            while ((length = is.read(bytes)) >= 0) {
                fos.write(bytes, 0, length);
            }
            is.close();
            fos.close();
        }
    }
}

