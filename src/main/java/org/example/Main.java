package org.example;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {
        String[] TARIFFS_INDEX = {"03", "06", "11"};
        String[] TARIFFS_NAME = {"Поминутный", "Тариф 300", "Обычный"};
        HashMap<BigInteger, Double> NumberAndMinutes = new HashMap<>();

        HashMap<BigInteger, ArrayList<User>> Users = new HashMap<>();

        System.out.println("Введите данные");

        File file = new File("src\\main\\java\\org\\example\\input.txt");
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String str_cdr = reader.readLine();
        String phone_number_str = "";
        while (str_cdr != null) {

            var cdr = str_cdr.split(", ");
            String start_time = cdr[2]; String end_time = cdr[3];
            phone_number_str = cdr[1];
            double minutes = count_minutes(start_time, end_time);
            String tarif_type = cdr[4];
            int tarif_index = find_element(tarif_type, TARIFFS_INDEX);
            BigInteger number = new BigInteger(phone_number_str);
            double all_minutes = minutes;
            if (NumberAndMinutes.containsKey(number)){
                all_minutes += NumberAndMinutes.get(number);
                NumberAndMinutes.put(number, all_minutes);
            }
            else {
                NumberAndMinutes.put(number, all_minutes);
            }
            String call_type = cdr[0];
            double cost = CountCost.count_cost(call_type, all_minutes, minutes, tarif_index);
            BigInteger Start = new BigInteger(start_time);
            BigInteger End = new BigInteger(end_time);
            User user = new User(call_type, Start, End, minutes, cost, tarif_index);
            if (Users.containsKey(number)){
                ArrayList<User> UserArray = Users.get(number);
                BigInteger user_start_date = user.start_time;
                int i = 0;
                boolean valid = true;
                while ((i < UserArray.size()) && (valid)){
                    BigInteger last_start_date = UserArray.get(i).start_time;
                    int comparevalue = user_start_date.compareTo(last_start_date);
                    if (comparevalue != 1){
                        valid = false;
                        UserArray.add(i, user);
                    }
                    i += 1;
                }
                if (valid){
                    UserArray.add(user);
                }
                Users.put(number, UserArray);
            }
            else {
                ArrayList<User> UserArray = new ArrayList<>(); UserArray.add(user);
                Users.put(number, UserArray);
            }

            str_cdr = reader.readLine();
        }

        List<BigInteger> keys = new ArrayList<BigInteger>(Users.keySet());

        for(int i = 0; i < keys.size(); i++) {
            BigInteger key = keys.get(i);

            ArrayList<User> user_array = Users.get(key);
            String filepath = "src\\main\\java\\org\\example\\answers\\" + String.valueOf(key) + ".txt";
            File ans = new File(filepath);
            String text = "Tariff index: " + String.valueOf(user_array.get(0).tarif_index) + "\n--------------------------------------------------------------------------------------\n";
            text += "Report for phone number: " + String.valueOf(key) + "\n--------------------------------------------------------------------------------------\n| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n--------------------------------------------------------------------------------------\n";
            for (int j = 0; j < user_array.size(); j ++){
                text += ("|     "+user_array.get(j).call_type + "    | " + user_array.get(j).start_time + " | " + user_array.get(j).end_time + " | " + user_array.get(j).duration + " | " + user_array.get(j).cost + " | " + "\n");
            }
            FileWriter writer = new FileWriter (ans);
            writer.write(text);
            writer.close();

        }

    }

    public static int find_element(String element, String[] Array){
        int ind = 0;
        for (int i = 0; i < Array.length; i ++){
            if (Objects.equals(element, Array[i])) ind = i;
        }
        return ind;
    }
    public static double count_minutes(String data_start, String data_end) throws ParseException {
        How_many_minutes how_many_minutes = new How_many_minutes();
        return how_many_minutes.dataToMinutes(data_start, data_end);
    }

}