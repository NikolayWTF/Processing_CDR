package org.example;

public class CountCost {

    public static double count_cost(String call_type, double all_minutes, double minutes, int tarif_index){

        double cost;
        if (tarif_index == 0) {
            cost = Tarifs.per_minute_rate(minutes);
        } else if (tarif_index == 1) {
            cost = Tarifs.Unlimited_300(all_minutes, minutes);
        } else {
            cost = Tarifs.Ordinary(call_type, all_minutes, minutes);
        }

        return cost;
    }
}
