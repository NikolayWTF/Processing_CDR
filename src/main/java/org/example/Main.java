package org.example;

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        String[] TARIFFS_INDEX = {"03", "06", "11"};
        HashMap<BigInteger, Double> NumberAndMinutes = new HashMap<>();
        // Ключ - номер телефона, значение - вся информация по этому номеру
        HashMap<BigInteger, ArrayList<User>> Users = new HashMap<>();

        //Ввод данных
        File file = new File("src\\main\\java\\org\\example\\input.txt");
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String str_cdr = reader.readLine();
        while (str_cdr != null) {

            // Считываю строку и делаю из неё массив
            var cdr = str_cdr.split(", ");
            String start_time = cdr[2]; String end_time = cdr[3];

            // Сколько минут длился разговор
            double minutes = count_minutes(start_time, end_time);

            // Узнаём индекс типа тарифа. 0 - 03, 1 - 06, 2 - 11
            String tariff_type = cdr[4];
            int tariff_index = find_element(tariff_type, TARIFFS_INDEX);

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

            // Создаю нового пользователя
            BigInteger Start = new BigInteger(start_time);
            BigInteger End = new BigInteger(end_time);
            User user = new User(call_type, Start, End, minutes, cost, tariff_index);

            // Если номер телефона уже есть - добавляем данные о новом звонке туда, иначе - добавляем номер
            ArrayList<User> UserArray;
            if (Users.containsKey(number)){
                UserArray = Users.get(number);
                BigInteger user_start_date = user.start_time;
                int i = 0;

                // Делаю так, чтобы звонки были отсортированы по времени
                boolean valid = true;
                while ((i < UserArray.size()) && (valid)){
                    BigInteger last_start_date = UserArray.get(i).start_time;
                    int compare_value = user_start_date.compareTo(last_start_date);
                    if (compare_value < 0){
                        valid = false;
                        UserArray.add(i, user);
                    }
                    i += 1;
                }
                if (valid){
                    UserArray.add(user);
                }
            }
            else {
                UserArray = new ArrayList<>();
                UserArray.add(user);
            }
            Users.put(number, UserArray);

            str_cdr = reader.readLine();
        }

        // Вывод всей информации
        List<BigInteger> keys = new ArrayList<>(Users.keySet());

        for (BigInteger key : keys) {
            ArrayList<User> user_array = Users.get(key);
            String filepath = "src\\main\\java\\org\\example\\answers\\" + key + ".txt";
            File ans = new File(filepath);
            StringBuilder text = new StringBuilder("Tariff index: " + user_array.get(0).tariff_index + "\n--------------------------------------------------------------------------------------\n");
            text.append("Report for phone number: ").append(key).append("\n--------------------------------------------------------------------------------------\n| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n--------------------------------------------------------------------------------------\n");
            String start_time_str; String end_time_str; String duration_str;
            for (User user : user_array) {
                start_time_str = Date_formating.Data_format(user.start_time);
                end_time_str = Date_formating.Data_format(user.end_time);
                duration_str = Duration_formating.Duration_format(user.duration);
                DecimalFormat decimalFormat = new DecimalFormat( "#.##" );
                text.append("|     ").append(user.call_type).append("    | ").append(start_time_str).append(" | ").append(end_time_str).append(" | ").append(duration_str).append(" | ").append(decimalFormat.format(user.cost)).append(" | ").append("\n");
            }
            FileWriter writer = new FileWriter(ans);
            writer.write(text.toString());
            writer.close();

        }

    }

    // Нахождение индекса элемента в массиве
    public static int find_element(String element, String[] Array){
        int ind = 0;
        for (int i = 0; i < Array.length; i ++){
            if (Objects.equals(element, Array[i])) ind = i;
        }
        return ind;
    }

    // Вычисление количества минут потраченных на разговор
    public static double count_minutes(String data_start, String data_end) throws ParseException {
        How_many_minutes how_many_minutes = new How_many_minutes();
        return how_many_minutes.dataToMinutes(data_start, data_end);
    }

}