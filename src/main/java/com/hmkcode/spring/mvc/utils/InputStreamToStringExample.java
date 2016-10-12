package com.hmkcode.spring.mvc.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Serzh on 10/12/16.
 */
public class InputStreamToStringExample {
    private static Set<Map<String, Integer>> maps = new LinkedHashSet<>();
    static Map<String, Integer> map1 = new LinkedHashMap<>();
    ;

    public static void main(String[] args) throws IOException {
        /*InputStream is = new ByteArrayInputStream("file content..blah blah".getBytes());
        String result = getStringFromInputStream(is);
        System.out.println(result);
        System.out.println("Done");*/
        String line1 = "Some string1";
        String line2 = "Some string1";
        String line3 = "Some string2";
        addLinesToMap(line1);
        addLinesToMap(line2);
        addLinesToMap(line3);
        System.out.println(map1.size());
        map1.forEach((s, integer) -> System.out.println(s + " : " + integer));
    }

    private static void addLinesToMap(String line) {
        if (map1.containsKey(line)) {
            map1.put(line, map1.get(line) + 1);
        } else {
            map1.put(line, 1);
        }
        map1.put("Some string2", 1);
    }

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
