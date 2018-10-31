package br.com.stanzione.uoltest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm");
    private static final SimpleDateFormat sdfDayMonth = new SimpleDateFormat("dd'/'MM");

    public static String formatHourMinute(Date date){
        if(null == date){
            return "";
        }
        else{
            return sdf.format(date);
        }
    }

    public static String formatDayMonth(Date date){
        if(null == date){
            return "";
        }
        else{
            return sdfDayMonth.format(date);
        }
    }

}
