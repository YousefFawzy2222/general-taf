package com.automationexercise.utils;

import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.logging.Logs;

public class TerminalUtils {
    public static void executeTerminalCommand(String... commandParts) {
        try {
            Process process = Runtime.getRuntime().exec(commandParts); // allure generate -o reports --single-file --clean
            int exitCode = process.waitFor();
            if (exitCode != 0){
                LogsManager.error("Command execution failed with exit code: " + exitCode);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to execute terminal command: " + String.join(" ", commandParts), e.getMessage());
        }
    }
}