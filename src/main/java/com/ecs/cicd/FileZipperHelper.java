package com.ecs.cicd;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.amazonaws.util.StringInputStream;

public class FileZipperHelper {

    private FileZipperHelper() {
    }

    static void writeFileAsZipIfChanged(String appspec) throws IOException {
        Path zipFile = Paths.get("asset/appspec.zip");

        if (isChangedFromZip(appspec, zipFile)) return;

        FileOutputStream fos = new FileOutputStream("asset/appspec.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        StringInputStream sis = new StringInputStream(appspec);
        ZipEntry zipEntry = new ZipEntry("appspec.yaml");
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = sis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        sis.close();
        fos.close();
    }

    private static boolean isChangedFromZip(String appspec,
                                            Path zipFile) throws IOException {
        if (Files.exists(zipFile)) {
            byte[] buffer = new byte[1024];
            InputStream inputStream = Files.newInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(inputStream);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                System.out.println("Unzipping file: " + fileName);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }

                String fileContentInZip = outputStream.toString(StandardCharsets.UTF_8);

                if (fileContentInZip.equals(appspec)) {
                    System.out.println("Skipped zipping of file again since the content is same as local zipped file in.");
                    return true;
                }

                outputStream.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            inputStream.close();
        }
        return false;
    }
}
