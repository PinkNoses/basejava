package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        int[] values = new int[9];
        for (int i = 0; i < values.length; i++) {
            values[i] = ThreadLocalRandom.current().nextInt(1, 10);
        }
        System.out.println("Initial array: " + Arrays.toString(values));
        System.out.println("Result of method 'minValue' : " + minValue(values));


        List<Integer> integers = new ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            integers.add(ThreadLocalRandom.current().nextInt(1, 10));
        }
        System.out.print("\nInitial list: ");
        integers.forEach(num -> System.out.print(num + ", "));
        System.out.println("\nResult of method 'oddOrEven' : " + oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Integer.parseInt(Arrays.stream(values).sorted().distinct()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining()));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .filter(integers.stream()
                        .mapToInt(Integer::intValue)
                        .sum() % 2 == 0 ? num -> num % 2 == 0 : num -> num % 2 != 0)
                .collect(Collectors.toList());
    }
}