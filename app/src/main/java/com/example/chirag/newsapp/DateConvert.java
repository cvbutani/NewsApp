package com.example.chirag.newsapp;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateConvert {

    public DateConvert() {
    }

    /**
     * Converts given String of dataTimeFormant into "2 Hours Ago" format.
     * @param dateStringFormat - Date and Time in String format
     * @return - 2 Hours Ago in String format.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String dateConverter(String dateStringFormat) {
        Date date = null;
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z");
        try {
            date = currentDateFormat.parse(dateStringFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat requireDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = requireDateFormat.format(date);

        long currentTimeInMilis = 0;

        try {
            Date currentDateObject = requireDateFormat.parse(currentDate);
            currentTimeInMilis = currentDateObject.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CharSequence timeSpanString = DateUtils.getRelativeTimeSpanString(currentTimeInMilis, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);

        return timeSpanString.toString();
    }
}
