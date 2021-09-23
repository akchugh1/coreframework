package com.qa.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
     * Converts date received from api call to correct format
     *
     * @param dateUtc
     *            date in milliseconds
     * @return date in format
     */
    public String convertDates(String dateUtc) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd MMMM YYYY");
        Date date = null;
        try {
            date = format1.parse(dateUtc);
        } catch (java.text.ParseException e) {
            System.out.println("Unable to parse date");
            e.printStackTrace();
        }
        return format2.format(date);

    }

}
