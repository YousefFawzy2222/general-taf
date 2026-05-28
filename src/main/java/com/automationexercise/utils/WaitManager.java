package com.automationexercise.utils;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class WaitManager {
    private WebDriver driver;

    public WaitManager(WebDriver driver){
        this.driver = driver;
    }

    public FluentWait<WebDriver> fluentWait(){
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoreAll(getExceptions()); //gets all expected Exceptions //takes Collection<Class<? extends K>> -> K represent the thing i want it to take and collection means an array list

    }
    //Class that extends Exception
    private ArrayList<Class<? extends Exception>> getExceptions(){
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(NoSuchElementException.class); //add.() adds to the ArrayList an element
        exceptions.add(StaleElementReferenceException.class);
        exceptions.add(ElementNotInteractableException.class);
        exceptions.add(ElementClickInterceptedException.class);
        return exceptions;
    }

}
