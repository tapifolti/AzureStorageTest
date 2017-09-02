package com.tapifolti.azurestorage.api;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by tapifolti on 8/29/2017.
 */
public class FilesFromZip {

    private static byte[] bytes = new byte[1024*100];

    public void extractAll(File fromZip, File toDirectory) throws IOException {
        toDirectory.mkdir();
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(fromZip));
        // ZipFile zipFile = new ZipFile(fromZip);
        // Enumeration<? extends ZipEntry> entries = zipFile.entries();
        // while(entries.hasMoreElements()) {
        ZipEntry entry = zipInputStream.getNextEntry();
        while (entry != null) {
            if (entry.isDirectory()) {
                String name = entry.getName();
                File dir = new File(toDirectory + File.separator + name);
                dir.mkdirs();
                continue;
            }
            String name = entry.getName();
            // also create folder referrred in 'name'
            File file = new File(toDirectory + File.separator + name);
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            // save file into toDirectory + subfolder
//            entry.
//            InputStream is = new InputStream(entry);
//            ZipInputStream zis = new ZipInputStream(entry);
//            FileOutputStream fos = new FileOutputStream(file);
//            try {
//                int length;
//                while ((length = is.read(bytes)) >= 0) {
//                    fos.write(bytes, 0, length);
//                }
//            } finally {
//                is.close();
//                fos.close();
//            }
//            // ...
            zipInputStream.closeEntry();
            entry = zipInputStream.getNextEntry();
        }
    }
}

