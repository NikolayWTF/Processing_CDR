package org.example;

import java.math.BigInteger;

public class Duration_formating {

    public static String Duration_format(Double duration){
        int duration_int = (int) Math.ceil(duration*60);
        int seconds = duration_int % 60;
        int minutes = ((duration_int - seconds)/60)%60;
        int hours = ((duration_int - seconds)/60 - ((duration_int - seconds)/60)%60)/60;
        String seconds_str; String minutes_str; String hours_str;
        if (seconds < 10) seconds_str = "0" + seconds;
        else seconds_str = String.valueOf(seconds);
        if (minutes < 10) minutes_str = "0" + minutes;
        else minutes_str = String.valueOf(minutes);
        if (hours < 10) hours_str = "0" + hours;
        else hours_str = String.valueOf(hours);
        String ans = hours_str + ":" + minutes_str + ":" + seconds_str;
        return ans;

    }
}
