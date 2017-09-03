package com.tapifolti.azurestorage.api;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by tapifolti on 8/29/2017.
 */
public class FilesFrom7Zip {
    final static Logger log = LoggerFactory.getLogger(FilesFrom7Zip.class);

    private static byte[] buff = new byte[1024 * 100];

    public void extractAll(File fromZip, File toDirectory) throws IOException {
        toDirectory.mkdir();
        // ZipFile(throws exception) and ZipInputStream(do not find any entry) does not work for the 7zip made archives
        // using apache commons compress
        try (SevenZFile input = new SevenZFile(fromZip)) {
            SevenZArchiveEntry entry = input.getNextEntry();
            while (entry != null ) {
                String name = entry.getName();
                if (entry.isDirectory()) {
                    File dir = new File(toDirectory, name);
                    dir.mkdirs();
                    entry = input.getNextEntry();
                    continue;
                }
                // also create folder referrred in 'name'
                File file = new File(toDirectory, name);
                File parent = file.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                // save file into toDirectory + subfolder
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                    int processed = 0;
                    while (processed < entry.getSize()) {
                        int bytesRead = input.read(buff);
                        bos.write(buff, 0, bytesRead);
                        processed += bytesRead;
                    }
                }
                entry = input.getNextEntry();
            }
        } catch (Exception ex) {
            log.error("Uncompress archive error", ex);
            throw new IOException(ex);
        }
    }
}

