package com.automationexercise.utils.report;

import com.automationexercise.media.ScreenRecordManager;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.automationexercise.utils.dataReader.PropertyReader.getProperty;
import static com.automationexercise.utils.logs.LogsManager.error;

public class AllureAttahcmentManager {

    // attachScreenshot, attachLogs, attachRecords methods would go here, but for simplicity, we will just implement attachScreenshot
    public static void attachScreenshot(String name, String path) {
        try {
            Path screenshot = Path.of(path);
            if (screenshot.toFile().exists()) {
                Allure.addAttachment(name, Files.newInputStream(screenshot));
            } else {
                LogsManager.error("Screenshot not found: " + path);
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching screenshot to Allure: " + e.getMessage());
        }

    }
    public static void attachLogs() {
        try {
            LogManager.shutdown();
            File logFile = new File(LogsManager.LOGS_PATH +  "logs.log");
            // Restart Log4j configuration
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            context.reconfigure();
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        }catch (Exception e){
            LogsManager.error("Error attaching logs to Allure: " + e.getMessage());
        }
    }
    public static void attachRecords(String testMethodName){
        if (getProperty("recordTests").equalsIgnoreCase("true")){
            try {
                File recordFile = new File(ScreenRecordManager.RECORDINGS_PATH + testMethodName);
                if (recordFile != null && recordFile.getName().endsWith(".mp4") ) {
                    Allure.addAttachment(testMethodName, "video/mp4", Files.newInputStream(recordFile.toPath()), ".mp4");
                }
            }catch (Exception e){
                LogsManager.error("Error attaching recording to Allure: " + e.getMessage());
            }
        }
    }
}


