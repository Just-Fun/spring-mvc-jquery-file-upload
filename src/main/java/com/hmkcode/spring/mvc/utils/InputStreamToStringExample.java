package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;

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

    public static void main(String[] args) throws IOException {
        PostgreSQLManager manager = new PostgreSQLManager();

        InputStream inputStream = manager.selectFile(10);
        getStringFromInputStream(inputStream);

        map1.forEach((s, integer) -> System.out.println(s + " : " + integer));
    }

    private static void addLinesToMap(String line) {
        if (map1.containsKey(line)) {
            map1.put(line, map1.get(line) + 1);
        } else {
            map1.put(line, 1);
        }
    }

    // convert InputStream to String
    public static void getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    addLinesToMap(line);
                }
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
    }
}
/*string = System.Text.Encoding.UTF8.GetString(byteArray);
//                        System.Text.Encoding.UTF8.GetString(buf).TrimEnd('\0');
//                        string = new String(bufferToString, StandardCharsets.UTF_8);
                        string = new String(bufferToString, StandardCharsets.UTF_8).trim();*/
