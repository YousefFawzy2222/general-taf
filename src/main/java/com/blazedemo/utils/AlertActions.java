package com.blazedemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AlertActions {
    private final WebDriver driver;
    private final WaitManager waitManager;

    public AlertActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }
    /**
     * Accept Alert
     */
    public void acceptAlert(){
        waitManager
                .fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().alert().accept();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }
    /**
     * Dismiss the alert
     */
    public void dismissAlert(){
        waitManager
                .fluentWait()
                .until(d -> {
                    try {
                        d.switchTo().alert().dismiss();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }
    /**
     * Gets the text from the alert
     * @return the text of the alert
     */
    public String getAlertText(){
        return waitManager
                .fluentWait()
                .until(d -> {
                    try {
                        String text = d.switchTo().alert().getText();
                        return !text.isEmpty() ? text : null; // if the text is not empty return it otherwise return null
                    } catch (Exception e) {
                        return null;
                    }
                });
    }

    /**
     * Sets the text in the alert
     * @param text the text to be set in the alert
     */
    public void setAlertText(String text){
        waitManager
                .fluentWait()
                .until(d ->{
                    try {
                        d.switchTo().alert().sendKeys(text);
                        return true;
                    }catch (Exception e){
                        return false;
                    }
        });
    }

}
