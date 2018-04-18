package com.example.android.movies.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by glm9637 on 11.03.2018 14:23.
 */

public final class Utils {

	private Utils(){
	
	}
    /**
     * parses a String with the patter yyyy-MM-dd to a date
     * @param dateValue the String to be parsed
     * @return the string was parsed the representing date, else null
     */
    public static Date parseToDate(String dateValue){
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateValue.contains("-")?"yyyy-MM-dd":"dd.MM.yyyy", Locale.US);
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
	
	public static Bitmap drawableToBitmap (Drawable drawable) {
		Bitmap bitmap = null;
		
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if(bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}
		
		if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}
		
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
