package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String way = generateRoute("RLRFR", 100);
                int cnt = getCountLetter(way);
                synchronized (sizeToFreq) {
                    sizeToFreq.merge(cnt, 1, Integer::sum);
                }
            }).start();
        }

        int maxValue = 0;
        Map.Entry<Integer, Integer> maxEntry = null;

        for (Map.Entry entry: sizeToFreq.entrySet()) {
            if (maxValue < (int) entry.getValue()) {
                maxEntry = entry;
                maxValue = maxEntry.getValue();
            }
        }

        System.out.println("Самое частое количество повторений " + maxEntry.getKey() +
                " (встретилось " + maxEntry.getValue() + " раз) ");
        System.out.println("Другие размеры:");
        for (Map.Entry entry: sizeToFreq.entrySet()) {
            if (maxEntry != entry) {
                System.out.print("- ");
                System.out.print(entry.getKey());
                System.out.print(" (");
                System.out.print(entry.getValue());
                System.out.println(" раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int getCountLetter(String way) {
        int cnt = 0;
        for (char symbol : way.toCharArray()) {
            if (symbol == 'R')
                cnt++;
        }
        return cnt;
    }
}