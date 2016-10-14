package com.hmkcode.spring.mvc;

import com.hmkcode.spring.mvc.result.JsonDocument;
import com.hmkcode.spring.mvc.utils.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Serzh on 10/14/16.
 */
public class Test {
    private static List<Map<String, Integer>> maps = new LinkedList<>();

    public static void main(String[] args) {
        Map<String, Integer> map1 = createMaps();
        Map<String, Integer> map2 = createMaps();
        Map<String, Integer> map3 = createMaps();
        Map<String, Integer> map4 = createMaps();
        Map<String, Integer> map5 = createMaps();
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
        maps.add(map5);

        Map<String, Integer> totalVisitCounts = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(Collectors.toMap(
                        entry -> entry.getKey(), // The key
                        entry -> entry.getValue(), // The value
                        // The "merger" as a method reference
                        Integer::sum
                        )
                );

        long begin = System.currentTimeMillis();
        Map<String, Integer> concatMaps = Service.concatMaps(maps);
        long end = System.currentTimeMillis();

        System.out.println("time: " + (end - begin));
        System.out.println("size map: " + concatMaps.size());
    }

    private static Map<String, Integer> createMaps() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String devd = "someString";
        int value = 1;
        for (int i = 0; i < 10000; i++) { // 10000 / 45 = 22 pages, 5 files 22 page each
            devd += "a";
            map.put(devd, value);
        }
        return map;
    }
}
