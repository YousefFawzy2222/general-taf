package com.automationexercise.utils.report;

import com.automationexercise.utils.OSUtils;
import com.automationexercise.utils.TerminalUtils;
import com.automationexercise.utils.logs.LogsManager;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
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

    public static void downloadAndExtract() {
        try{
            String version = LazyHolder.VERSION;
            Path extractionDir = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), "allure-" + version);
            // C:\Users\yousef\.m2\repository\allure\allure-2.42.0
            // if it exists skip
            if(Files.exists(extractionDir)){
                LogsManager.info("Allure binaries already exist for version " + version);
                return;
            }

            // Give execute permission to the binary if not on Windows (MacOS, Linux)
            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)){
                TerminalUtils.executeTerminalCommand("chmod", "u+x", AllureConstant.USER_DIR.toString());
            }

            Path zipPath = downloadZip(version);
            extractZip(zipPath);

            LogsManager.info("Allure binaries downloaded and extracted successfully for version " + version);

            // Give execute permission to the binary if not on Windows (MacOS, Linux)
            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)){
                TerminalUtils.executeTerminalCommand("chmod", "u+x", getExecutable().toString());
            }

            //Clean up the zip file after extraction
            Files.deleteIfExists(Files.list(AllureConstant.EXTRACTION_DIR).filter(path -> path.toString().endsWith(".zip")).findFirst().orElseThrow(() -> new IOException("No ZIP file found for cleanup")));
        }catch (Exception e){
            LogsManager.error("Error in downloading and extracting Allure binaries: " + e.getMessage());
        }
    }

    public static Path getExecutable() {
        String version = LazyHolder.VERSION;
        Path binaryPath = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), "allure-" + version, "bin", "allure");
        return OSUtils.getCurrentOS() == OSUtils.OS.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName() + ".bat") // If windows, look for allure.bat
                : binaryPath; // if not Windows look for allure
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
                try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
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
