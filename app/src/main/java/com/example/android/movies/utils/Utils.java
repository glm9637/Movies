package com.example.android.movies.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by glm9637 on 11.03.2018 14:23.
 */

public class Utils {


    /**
     * parses a String with the patter yyyy-MM-dd to a date
     * @param dateValue the String to be parsed
     * @return the string was parsed the representing date, else null
     */
    public static Date parseToDate(String dateValue){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            return dateFormat.parse(dateValue);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * parses a Date to a String with the pattern MM.dd.yyyy
     * @param date the Date to be parsed
     * @return the parsed String
     */
    public static String parseToString(Date date){
    	if(date == null){
    		return "";
	    }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy",Locale.US);
        return dateFormat.format(date);
    }
}
