package com.automationexercise.customelisteners;

import com.automationexercise.FileUtils;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.media.ScreenRecordManager;
import com.automationexercise.media.ScreenshotsManager;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import com.automationexercise.utils.report.AllureAttahcmentManager;
import com.automationexercise.utils.report.AllureConstant;
import com.automationexercise.utils.report.AllureEnvironmentManager;
import com.automationexercise.utils.report.AllureReportGenerator;
import com.automationexercise.validations.Validation;
import org.json.Property;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener {
    @Override
    public void onExecutionStart() {
        LogsManager.info("Test Execution started");

        cleanTestOutputDirectories();
        LogsManager.info("Test output directories cleaned");

        createTestOutputDirectories();
        LogsManager.info("Test output directories created");

        PropertyReader.loadProperties();
        LogsManager.info("Properties files loaded");

        AllureEnvironmentManager.setAllureEnvironment();
        LogsManager.info("Allure environment set");

    }

    @Override
    public void onExecutionFinish() {
        AllureReportGenerator.generateReports(false);
        LogsManager.info("Allure report generated");

        AllureReportGenerator.copyHistory();
        LogsManager.info("Allure history copied");

        AllureReportGenerator.generateReports(true);
        LogsManager.info("Single file Allure Report generated");

        AllureReportGenerator.openReport(AllureReportGenerator.renameReportFile());
        LogsManager.info("Allure report opened in browser");

        LogsManager.info("Test Execution finished");
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        WebDriver driver = null;
        if (method.isTestMethod()){
            ScreenRecordManager.stopRecording(testResult.getName());
            Validation.assertAll();
            if (testResult.getInstance() instanceof WebDriverProvider provider){
                driver = provider.getWebDriver(); //Intialized driver fro WebDriverProvider
            }
            switch (testResult.getStatus()){
                case ITestResult.SUCCESS -> ScreenshotsManager.takeFullPageScreenshot(driver, "passed-" + testResult.getName());
                case ITestResult.FAILURE -> ScreenshotsManager.takeFullPageScreenshot(driver, "failed-" + testResult.getName());
                case ITestResult.SKIP -> ScreenshotsManager.takeFullPageScreenshot(driver, "skipped-" + testResult.getName());
            }
            AllureAttahcmentManager.attachLogs();
            AllureAttahcmentManager.attachRecords(testResult.getName());

        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        if (method.isTestMethod()){
            ScreenRecordManager.startRecording();
            LogsManager.info("Started recording for test: " + method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogsManager.info("Test failed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test skipped: " + result.getMethod().getMethodName());
    }


    // Cleaning and Creating dirs (logs, screenshoots, recordings, allure results)
    public void cleanTestOutputDirectories(){
        FileUtils.cleanDirectory(AllureConstant.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
        FileUtils.cleanDirectory(new File(LogsManager.LOGS_PATH));

    }

    //screenshoots, recordings
    public void createTestOutputDirectories(){
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_PATH);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
    }


}
