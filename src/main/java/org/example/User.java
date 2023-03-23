// Вся информация по звонку, кроме номера телефона
package org.example;

import java.math.BigInteger;

public class User {

    String call_type;
    BigInteger start_time;
    BigInteger end_time;
    double duration;
    double cost;
    Integer tariff_index;
    public User(String call_type, BigInteger start_time, BigInteger end_time, double duration, double cost, Integer tariff_index){
        this.call_type = call_type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.duration = duration;
        this.cost = cost;
        this.tariff_index = tariff_index;

    }


}
