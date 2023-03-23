package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class How_many_minutes {

    public double dataToMinutes(String start_data, String end_data)
            throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
        Date firstDate = sdf.parse(start_data);
        Date secondDate = sdf.parse(end_data);

        long diffInMillisecond = Math.abs(secondDate.getTime() - firstDate.getTime());
        double seconds = (int) TimeUnit.SECONDS.convert(diffInMillisecond, TimeUnit.MILLISECONDS);

        return seconds/60;
    }

}
