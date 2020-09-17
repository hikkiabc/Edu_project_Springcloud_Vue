package com.gg.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date getDayFromToday(Date date,Integer dayNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +dayNum);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }
}
