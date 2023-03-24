// Вся информация по звонку, кроме номера телефона
package org.example;

import java.math.BigInteger;

public class Call {

    String call_type;
    BigInteger start_time;
    BigInteger end_time;
    double duration;
    double cost;
    String tariff_index;
    public Call(String call_type, BigInteger start_time, BigInteger end_time, double duration, double cost, String tariff_index){
        this.call_type = call_type;
        this.start_time = start_time;
        this.end_time = end_time;
        this.duration = duration;
        this.cost = cost;
        this.tariff_index = tariff_index;

    }


}
