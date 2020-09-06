package com.urise.webapp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MainStream {
    public static void main(String[] args) {

        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(minValue(new int[]{9, 5, 0, 6, 4, 6, 2}));

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        oddOrEven(list).forEach(System.out::println);

        list.add(5);
        oddOrEven(list).forEach(System.out::println);
    }

    private static int minValue(int[] values) {
        List<Integer> list = IntStream.of(values)
                .distinct()
                .sorted()
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return list.stream()
                .map(x -> (int) (Math.pow(10, list.size() - list.indexOf(x) - 1)) * x)
                .mapToInt(x -> x)
                .sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(x -> (x % 2 == 0)));
        return (integers.stream()
                .reduce(0, Integer::sum) % 2 == 0)
                ? map.get(true)
                : map.get(false);
    }
}
