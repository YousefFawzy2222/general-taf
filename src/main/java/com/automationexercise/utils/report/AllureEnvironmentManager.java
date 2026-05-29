package com.automationexercise.utils.report;

import com.automationexercise.utils.logs.LogsManager;
import com.google.common.collect.ImmutableMap;

import java.io.File;

import static com.automationexercise.utils.dataReader.PropertyReader.getProperty;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {
    void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", getProperty("os.name"))
                        .put("Java version:", getProperty("java.runtime.version"))
                        .put("Browser", getProperty("browserType"))
                        .put("Execution Type", getProperty("executionType"))
                        .put("URL", getProperty("baseUrlWeb"))
                        .build(), String.valueOf(AllureConstant.RESULTS_FOLDER) + File.separator);
        LogsManager.info("Allure environment properties set successfully.");
        AllureBunaryManager.downloadAndExtract();

    }
}
