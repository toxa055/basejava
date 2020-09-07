package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MainStream {
    public static void main(String[] args) {

        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(minValue(new int[]{9, 5, 6, 4, 6, 2}));

        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
        }
        oddOrEven(list).forEach(System.out::println);

        list.add(5);
        oddOrEven(list).forEach(System.out::println);
    }

    private static int minValue(int[] values) {
        return IntStream.of(values)
                .sorted()
                .distinct()
                .reduce(0, (x, y) -> x * 10 + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> (x % 2 == 0)));
        return map.get(map.get(false).size() % 2 == 0);
    }
}
