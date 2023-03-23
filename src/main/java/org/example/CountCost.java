// Тут выбираю нужный тариф

package org.example;

public class CountCost {

    public static double count_cost(String call_type, double all_minutes, double minutes, int tariff_index){

        double cost;
        if (tariff_index == 0) {
            cost = Tariffs.per_minute_rate(minutes);
        } else if (tariff_index == 1) {
            cost = Tariffs.Unlimited_300(all_minutes, minutes);
        } else {
            cost = Tariffs.Ordinary(call_type, all_minutes, minutes);
        }

        return cost;
    }
}
