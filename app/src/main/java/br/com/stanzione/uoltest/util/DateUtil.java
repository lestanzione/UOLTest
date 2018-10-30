package br.com.stanzione.uoltest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm");

    public static String formatHourMinute(Date date){
        if(null == date){
            return "";
        }
        else{
            return sdf.format(date);
        }
    }

}
