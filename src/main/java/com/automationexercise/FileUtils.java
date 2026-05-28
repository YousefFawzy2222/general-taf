package com.automationexercise;

import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;

import java.io.File;

public class FileUtils {
    private static final String USER_DIR =PropertyReader.getProperty("user.dir") + File.separator;
    private FileUtils() {
        // Private constructor to prevent instantiation
    }

    // Renaming
    public static void renameFile(String oldName, String newName){
        try{
            File oldFile = new File(USER_DIR + oldName);
            File newFile = new File(USER_DIR + newName);
            if (oldFile.renameTo(newFile)) {
                LogsManager.info("File renamed from " + oldName + " to " + newName);
            } else {
                LogsManager.error("Failed to rename file: " + oldName);
            }
        }catch (Exception e){
            LogsManager.error("Error renaming file: " + e.getMessage());
        }
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

}
