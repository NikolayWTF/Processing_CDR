package org.example;

import java.math.BigInteger;

public class Date_formating {

    public static String Data_format(BigInteger data){
        String data_str = String.valueOf(data);
        String year = data_str.substring(0, 4);
        String month = data_str.substring(4, 6);
        String day =  data_str.substring(6, 8);
        String hour =  data_str.substring(8, 10);
        String minute =  data_str.substring(10, 12);
        String second =  data_str.substring(12, 14);
        String ans = year + "-" + month + "-" + day + " " + hour + "-" + minute + "-" + second;
        return ans;

    }
}

