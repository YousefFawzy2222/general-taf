package com.blazedemo.utils;

import java.text.SimpleDateFormat;

public class TimeManager {
    //screenshots - logs - reports
    public static String getTimeStamp(){
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new java.util.Date());
    }
    //unique timestamp for each data
    public static String getSimpleTimeStamp(){
        //return a timestamp in numbers only
        return Long.toString(System.currentTimeMillis());
    }
}
