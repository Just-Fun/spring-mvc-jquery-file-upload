package com.hmkcode.spring.mvc;

import com.hmkcode.spring.mvc.utils.Service;

import java.util.*;

/**
 * Created by Serzh on 10/14/16.
 */
public class Test {
    private static List<Map<String, Integer>> maps = new ArrayList<>();

    public static void main(String[] args) {
        createMaps();

        long begin = System.currentTimeMillis();
        Map<String, Integer> result = new Service().concatMaps(maps);
        long end = System.currentTimeMillis();

        System.out.println("time: " + (end - begin));
        System.out.println("size map: " + result.size());
    }

    private static Map<String, Integer> createMap() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String devd = "someString";
        int value = 1;
        for (int i = 0; i < 10000; i++) { // 10000 / 45 = 22 pages, 5 files 22 page each
            devd += "a";
            map.put(devd, value);
        }
        return map;
    }

    private static void createMaps() {
        Map<String, Integer> map1 = createMap();
        Map<String, Integer> map2 = createMap();
        Map<String, Integer> map3 = createMap();
        Map<String, Integer> map4 = createMap();
        Map<String, Integer> map5 = createMap();
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
        maps.add(map4);
        maps.add(map5);
    }
}
