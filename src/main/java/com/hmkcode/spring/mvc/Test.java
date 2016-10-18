package com.hmkcode.spring.mvc;

import com.hmkcode.spring.mvc.service.Parser;
import com.hmkcode.spring.mvc.service.Service;

import java.io.*;
import java.util.*;

/**
 * Created by Serzh on 10/14/16.
 */
public class Test {
    private static final String FILE1 = "src/test/resources/fileLong.txt";
    private static List<Map<String, Integer>> maps = new ArrayList<>();
    static Parser parser;

    public static void main(String[] args) {
        parser = new Parser();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            InputStream inputStream = getFile(FILE1);
            parser.createMapFromFile(inputStream);
        }
        Map<String, Integer> result = parser.getResult();
        long end = System.currentTimeMillis();

        result.forEach((k, v) -> System.out.println(k.toString() + " : " + v.toString()) );
        System.out.println("result.size()" + result.size());
        System.out.println("time: " + (end - begin));
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

    public static void writeToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/test/resources/fileLong.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(maps); // ?
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in src/test/resources/fileLong.txt");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static InputStream getFile(String path) {
        File initialFile = new File(path);
        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return targetStream;
    }
}
