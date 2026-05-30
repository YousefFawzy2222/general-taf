package com.automationexercise.validations;

import com.automationexercise.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

// Soft Assertion
public class Validation extends BaseAssertion {
    private static SoftAssert softAssert = new SoftAssert();
    private static boolean used = false; //Flag to track usage of softAssertion
    public Validation(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        used = true;
        softAssert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        used = true;
        softAssert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(Object actual, Object expected, String message) {
        used = true;
        softAssert.assertEquals(actual, expected, message);
    }

    public static void assertAll(){
        if(!used) return; // If no assertions were made, do nothing
        try{
            softAssert.assertAll();
        }
        catch (AssertionError e){
            LogsManager.error("Assertion failed: " + e.getMessage());
            throw e;
        }
        finally {
            softAssert = new SoftAssert(); // Reset softAssert for the next test
        }
    }
}
