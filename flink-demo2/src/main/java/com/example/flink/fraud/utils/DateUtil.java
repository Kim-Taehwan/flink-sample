package com.example.flink.fraud.utils;

import java.text.SimpleDateFormat;

public class DateUtil {

    public static String longToDate(long timer) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(timer);
        System.out.println("RegisterProcessingTimeTimer : " + date);
        return date;
    }

}
