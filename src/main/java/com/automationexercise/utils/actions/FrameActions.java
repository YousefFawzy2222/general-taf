package com.automationexercise.utils.actions;

import com.automationexercise.utils.WaitManager;
import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public FrameActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    /**
     * Switch to a frame by index
     * @param index the index of the frame
     */
    public void switchToFrameByIndex(int index){
        waitManager
                .fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().frame(index);
                        LogsManager.info("Switched to frame with index: " + index);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }
    /**
     * Switch to a frame by name or ID
     * @param nameOrId the name or ID of the frame
     */
    public void switchToFrameByNameOrId(String nameOrId){
        waitManager
                .fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().frame(nameOrId);
                        LogsManager.info("Switched to frame with name or ID: " + nameOrId);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }
    /**
     * Switch to a frame by WebElement
     * @param frameLocator the locator of the frame
     */
    public void switchToFrameByElement(By frameLocator) {
    waitManager
            .fluentWait()
            .until(d -> {
                try {
                    d.switchTo().frame(d.findElement(frameLocator));
                    LogsManager.info("Switched to frame with locator: " + frameLocator);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            });
    }
    /**
     * Switch back to the default content
     */
    public void switchToDefaultContent(){
        waitManager
                .fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().defaultContent();
                        LogsManager.info("Switched back to default content");
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }

}
