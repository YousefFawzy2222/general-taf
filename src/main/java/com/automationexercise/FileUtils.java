package com.automationexercise;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.commons.io.FileUtils.copyFile;

public class FileUtils {
    private static final String USER_DIR =PropertyReader.getProperty("user.dir") + File.separator;
    private FileUtils() {
        // Private constructor to prevent instantiation
    }

    // Renaming
    public static void renameFile(String oldName, String newName) {
        try {
            var targetFile = new File(oldName);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDirectory + File.separator + newName);
            if(!targetFile.getPath().equals(newFile.getPath())){
                copyFile(targetFile,newFile);
                org.apache.commons.io.FileUtils.deleteQuietly(targetFile);
                LogsManager.info("File renamed from " + oldName + " to " + newName);
            }else {
                LogsManager.info("Old name and new name are the same. No renaming needed for: " + oldName);
            }

        } catch (Exception e) {
            LogsManager.error("Error renaming file from " + oldName + " to " + newName + ": " + e.getMessage());
        }
    }

    private static Path resolvePath(String path) {
        Path inputPath = Paths.get(path);

        if (inputPath.isAbsolute()) {
            return inputPath.normalize();
        }

        return Paths.get(PropertyReader.getProperty("user.dir"))
                .resolve(inputPath)
                .normalize();
    }


    // Creating Directory
    public static void createDirectory(String path){
        try{
            File file = new File(USER_DIR + path);
            if (!file.exists()) {
                file.mkdirs();
                LogsManager.info("Directory created at: " + path);
            }
        }catch (Exception e){
            LogsManager.error("Failed creating directory: " + e.getMessage());
        }
    }

    // Cleaning Directory
    public static void cleanDirectory(File file){
        try{
            org.apache.commons.io.FileUtils.deleteQuietly(file);
        }catch (Exception e){
            LogsManager.error("Error cleaning directory: " + e.getMessage());
        }
    }

    public static void copyDirectory(Path historyFolder, Path resultsHistoryFolder) {
        try {
            org.apache.commons.io.FileUtils.copyDirectory(historyFolder.toFile(), resultsHistoryFolder.toFile());
            LogsManager.info("Directory copied from " + historyFolder + " to " + resultsHistoryFolder);
        } catch (Exception e) {
            LogsManager.error("Error copying directory: " + e.getMessage());
        }
    }
}
