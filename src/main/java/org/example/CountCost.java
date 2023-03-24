// Тут выбираю нужный тариф

package org.example;

import java.util.Objects;

public class CountCost {

    public static double count_cost(String call_type, double all_minutes, double minutes, String tariff_index){

        double cost;
        if (Objects.equals(tariff_index, "03")) {
            cost = Tariffs.per_minute_rate(minutes);
        } else if (Objects.equals(tariff_index, "06")) {
            cost = Tariffs.Unlimited_300(all_minutes, minutes);
        } else {
            cost = Tariffs.Ordinary(call_type, all_minutes, minutes);
        }

        return cost;
    }
}
