// Тут вычисляется цена разговора для каждого тарифа

package org.example;

import java.util.Objects;

public class Tariffs {

    public static double per_minute_rate(double minutes){
        return minutes * 1.5;
    }

    public static double Unlimited_300(double all_minutes, double minutes){
        double cost;
        if (all_minutes <= 300){
            cost = 0;
        } else if (all_minutes - minutes > 300) {
            cost = minutes;
        }
        else {
            cost = (all_minutes - 300);
        }


        return cost;
    }

    public static double Ordinary(String call_type, double all_minutes, double minutes){
        double cost;
        if (Objects.equals(call_type, "02")){
            cost = 0;
        }
        else {
            if (all_minutes <= 100){
                cost = 0;
            } else if (all_minutes - minutes > 100) {
                cost = minutes * 1.5;
            }
            else {
                cost = (all_minutes - 100) * 0.5;
            }
        }

        return cost;
    }
}
