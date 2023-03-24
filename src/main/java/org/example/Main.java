package org.example;

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {

        HashMap<BigInteger, Double> NumberAndMinutes = new HashMap<>();
        // Ключ - номер телефона, значение - вся информация по этому номеру
        HashMap<BigInteger, ArrayList<Call>> Cals = new HashMap<>();

        //Ввод данных
        File file = new File("src\\main\\java\\org\\example\\cdr.txt");
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String str_cdr = reader.readLine();
        while (str_cdr != null) {

            // Считываю строку и делаю из неё массив
            var cdr = str_cdr.split(", ");
            String start_time = cdr[2]; String end_time = cdr[3];

            // Сколько минут длился разговор
            double minutes = count_minutes(start_time, end_time);

            // Узнаём типа тарифа.
            String tariff_index = cdr[4];

            // Это мапа с номером телефона и суммарным количеством минут за все звонки
            BigInteger number = new BigInteger(cdr[1]);
            double all_minutes = minutes;
            if (NumberAndMinutes.containsKey(number)){
                all_minutes += NumberAndMinutes.get(number);
                NumberAndMinutes.put(number, all_minutes);
            }
            else {
                NumberAndMinutes.put(number, all_minutes);
            }

            // Считаем стоимость разговора
            String call_type = cdr[0];
            double cost = CountCost.count_cost(call_type, all_minutes, minutes, tariff_index);

            // Создаю новый звонок
            BigInteger Start = new BigInteger(start_time);
            BigInteger End = new BigInteger(end_time);
            Call call = new Call(call_type, Start, End, minutes, cost, tariff_index);

            // Если номер телефона уже есть - добавляем данные о новом звонке туда, иначе - добавляем номер
            ArrayList<Call> callArray;
            if (Cals.containsKey(number)){
                callArray = Cals.get(number);
                BigInteger call_start_date = call.start_time;
                int i = 0;

                // Делаю так, чтобы звонки были отсортированы по времени
                boolean valid = true;
                while ((i < callArray.size()) && (valid)){
                    BigInteger last_start_date = callArray.get(i).start_time;
                    int compare_value = call_start_date.compareTo(last_start_date);
                    if (compare_value < 0){
                        valid = false;
                        callArray.add(i, call);
                    }
                    i += 1;
                }
                if (valid){
                    callArray.add(call);
                }
            }
            else {
                callArray = new ArrayList<>();
                callArray.add(call);
            }
            Cals.put(number, callArray);

            str_cdr = reader.readLine();
        }

        // Вывод всей информации
        List<BigInteger> keys = new ArrayList<>(Cals.keySet());

        for (BigInteger key : keys) {
            ArrayList<Call> call_array = Cals.get(key);
            String filepath = "src\\main\\java\\org\\example\\answers\\" + key + ".txt";
            File ans = new File(filepath);
            StringBuilder text = new StringBuilder("Tariff index: " + call_array.get(0).tariff_index + "\n--------------------------------------------------------------------------------------\n");
            text.append("Report for phone number: ").append(key).append("\n--------------------------------------------------------------------------------------\n| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n--------------------------------------------------------------------------------------\n");
            String start_time_str; String end_time_str; String duration_str;
            for (Call call : call_array) {
                start_time_str = Date_formating.Data_format(call.start_time);
                end_time_str = Date_formating.Data_format(call.end_time);
                duration_str = Duration_formating.Duration_format(call.duration);
                DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
                text.append("|     ").append(call.call_type).append("    | ").append(start_time_str).append(" | ").append(end_time_str).append(" | ").append(duration_str).append(" | ").append(decimalFormat.format(call.cost)).append(" | ").append("\n");
            }
            FileWriter writer = new FileWriter(ans);
            writer.write(text.toString());
            writer.close();

        }

    }

    // Вычисление количества минут потраченных на разговор
    public static double count_minutes(String data_start, String data_end) throws ParseException {
        How_many_minutes how_many_minutes = new How_many_minutes();
        return how_many_minutes.dataToMinutes(data_start, data_end);
    }

}