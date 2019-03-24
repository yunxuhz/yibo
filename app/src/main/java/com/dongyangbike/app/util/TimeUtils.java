package com.dongyangbike.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String getNormalTime(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        String time = format.format(new Date(value)) ;
        return time;
    }
}
