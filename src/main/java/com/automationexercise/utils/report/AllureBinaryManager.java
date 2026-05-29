package com.automationexercise.utils.report;

import com.automationexercise.utils.logs.LogsManager;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureBinaryManager {
    private static class LazyHolder {
        static final String VERSION = resolveVersion();

        // The function is mainly about opening Allure's github to find the latest version of allure to auto-download
        private static String resolveVersion() {
            try {
                String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest").followRedirects(true).execute().url().toString();
                return url.split("/tag/")[1];
            } catch (IOException e) {
                throw new RuntimeException("Failed to resolve Allure version: " + e.getMessage(), e);
            }
        }
    }

    // Download ZIP file for Allure
    private static Path downloadZip(String version) {
        try {
            // https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.42.0/allure-commandline-2.42.0.zip
            String url = AllureConstant.ALLURE_ZIP_BASE_URL + version + "/allure-commandline-" + version + ".zip";
            // C:\Users\yousef\.m2\repository\allure
            Path zipFile = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), "allure-" + version + ".zip");

            if (!Files.exists(zipFile)) {
                Files.createDirectories(AllureConstant.EXTRACTION_DIR);
                try (BufferedInputStream in = new BufferedInputStream(new URL(url).toURI().toURL().openStream());
                     OutputStream out = Files.newOutputStream(zipFile)) {
                    in.transferTo(out);
                } catch (Exception e) {
                    LogsManager.error("Failed to download Allure binary: " + e.getMessage());
                }
            }
            return zipFile;
        } catch (Exception e){
            LogsManager.error("Error preparing to download Allure binary: " + e.getMessage());
            return Paths.get("");
        }

    }

    // Extract ZIP file for Allure
    private static void extractZip(Path zipPath){
        try(ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path filePath = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream, filePath);
                }
            }
        } catch (IOException e) {
            LogsManager.error("Failed to extract Allure binary: " + e.getMessage());
        }
    }
}
