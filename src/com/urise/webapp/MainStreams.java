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

        List<Integer> integers = new ArrayList<>(6);
        for (int i = 0; i < 9; i++) {
            integers.add(ThreadLocalRandom.current().nextInt(1, 10));
        }
        System.out.print("\nInitial list: ");
        integers.forEach(num -> System.out.print(num + ", "));
        System.out.println("\nResult of method 'oddOrEven' : " + oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        final int uniqueElementCount = (int) Arrays.stream(values).distinct().count();
        System.out.println("uniqueElementCount is " + uniqueElementCount);
        return Arrays.stream(values).sorted().distinct()
                .reduce(0, (x, y) -> 10 * x + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        final int sum = integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
        return integers.stream()
                .filter(num -> (sum % 2 == 0) == (num % 2 == 0))
                .collect(Collectors.toList());
    }
}