package com.hmkcode.spring.mvc.utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by Serzh on 10/8/16.
 */
public class Parser {

    //    private static Map<String, Integer> map;
    private static Set<Map<String, Integer>> maps = new LinkedHashSet<>();
    private static List<Map<String, Integer>> listOfMaps = new LinkedList<>();
    private static final byte DELIMITER = (byte) '\n';

    public static void main(String[] args) {
        /*String big = "ihbwl5kef;5ljl6;k;k2lkde4;k;k5";
        int allOccurrences = findAllOccurrences(big, "5");
        System.out.println(allOccurrences);*/
        Map<String, Integer> map1 = new LinkedHashMap<>();
        Map<String, Integer> map2 = new LinkedHashMap<>();
        Map<String, Integer> map3 = new LinkedHashMap<>();
        //map.put(key, map.get(key) + 1);
//        String some1 = "Some string";
//        map1.put(some1, map1.get(some1) + 1);
        String some1 = "Some string";
        map1.put(some1, 1);
        if (map1.containsKey(some1)) {
            map1.put(some1, map1.get(some1) + 1);
        } else {
            map1.put(some1, 1);
        }
        map1.put("Some string2", 1);
        System.out.println(map1.size());
        map1.forEach((s, integer) -> System.out.println(s + " : " + integer));
      /*  map1.put("Some string", 0);
        map2.put("Some string", 0);
        map3.put("Some string2", 0);
        listOfMaps.add(map1);
        listOfMaps.add(map2);
        listOfMaps.add(map3);*/

//        String str = new String(bytes, StandardCharsets.UTF_8);

//        String str = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        String s = "ksdckbs\nfvl\ndfvf\n\n";
//        byte[] bytes = s.getBytes();
        byte[] bytes = s.getBytes(Charset.forName("UTF-8"));
        /*for (byte b : bytes) {
            String oneByteString = new String(new byte[] { b });
            System.out.println(b);
            System.out.println(oneByteString);
        }*/

       /* byte DELIMITER = (byte) '\n';
        byte[] bytes1 = "\n".getBytes(Charset.forName("UTF-8"));

        System.out.println(DELIMITER);
        System.out.println(bytes1[0]);
//        findString(s);
        System.out.println(maps.size());*/
    }

    private static void findCharsFromBytes(byte[] bytes) {
        int count = 0;
        for (int i = 0; i < bytes.length; i++) {

        }
    }

    private static void findStringFromBytes(byte[] bytes) {
        String s = new String(bytes);
        String[] splitS = s.split("\n");
        for (int i = 0; i < splitS.length; i++) {
            if (!splitS[i].isEmpty()) {
                System.out.println(splitS[i]);
            }
        }
    }

    private static void findString(String s) {
        String[] splitS = s.split("\n");
        for (int i = 0; i < splitS.length; i++) {
            if (!splitS[i].isEmpty()) {
                System.out.println(splitS[i]);
            }
        }
    }

    public static int findAllOccurrences(String fromFile, String find) {
        int count = StringUtils.countMatches(fromFile, find);
        return count;
    }

    public static void run2(List<Map<String, Integer>> listOfMaps) {
        Set<Map<String, Integer>> maps = new LinkedHashSet<>();
        for (Map<String, Integer> map : listOfMaps) {
            if (!maps.add(map) == true) {
            }
        }
    }

    public static void run(List<Map<String, Integer>> listOfMaps) {
        Set<Map<String, Integer>> maps = new LinkedHashSet<>();
        for (Map<String, Integer> map : listOfMaps) {
            if (!maps.add(map) == true) {
            }
        }
    }
    /*Spring Framework's oneliner for this is:
        int occurance = StringUtils.countOccurrencesOf("a.b.c.d", ".");*/

}
