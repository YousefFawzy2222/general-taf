package com.automationexercise.utils.report;

import com.automationexercise.FileUtils;
import com.automationexercise.utils.OSUtils;
import com.automationexercise.utils.TerminalUtils;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.logs.LogsManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.automationexercise.utils.dataReader.PropertyReader.getProperty;

public class AllureReportGenerator {
    //Generate Allure Report
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? AllureConstant.REPORT_PATH : AllureConstant.FULL_REPORT_PATH;
        // allure generate -o reports --single-file --clean
        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                AllureConstant.RESULTS_FOLDER.toString(),
                "-o",
                outputFolder.toString(),
                "--clean"
        ));
        if (isSingleFile) command.add("--single-file");
        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }

    //Open Allure report in browser
    public static void openReport(String reportFileName) {
        if (!getProperty("executionType").toLowerCase().contains("local")) return;

        Path reportPath = AllureConstant.REPORT_PATH.resolve(reportFileName);
        switch (OSUtils.getCurrentOS()) {
            case WINDOWS -> TerminalUtils.executeTerminalCommand("cmd", "/c", "start", reportPath.toString());
            case MAC, LINUX -> TerminalUtils.executeTerminalCommand("open", reportPath.toString());
            default ->
                    LogsManager.warn("Unsupported OS for opening Allure report automatically. Please open the report manually at: " + reportPath);
        }

    }

    // Copy history folder to results folder
    public static void copyHistory() {
        try {
            FileUtils.copyDirectory(AllureConstant.HISTORY_FOLDER, AllureConstant.RESULTS_HISTORY_FOLDER);
        } catch (Exception e) {
            LogsManager.error("Failed to copy history folder: " + e.getMessage());
        }
    }

    //Rename report file to AllureReport_timestamp.html
    public static String renameReportFile() {
        String newFileName = AllureConstant.REPORT_PREFIX + TimeManager.getTimeStamp() + AllureConstant.REPORT_EXTENSION;
        FileUtils.renameFile(AllureConstant.REPORT_PATH.resolve(AllureConstant.INDEX_HTML).toString(), newFileName);
        return newFileName;
    }
}
