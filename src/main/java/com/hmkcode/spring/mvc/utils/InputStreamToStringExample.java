package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Serzh on 10/12/16.
 */
public class InputStreamToStringExample {
    private static List<Map<String, Integer>> maps = new LinkedList<>();
    static Map<String, Integer> map;

    public static void main(String[] args) throws IOException {
        PostgreSQLManager manager = new PostgreSQLManager();

        InputStream inputStream = manager.selectFile(10);
        new InputStreamToStringExample().getStringFromInputStream(inputStream);

        for (Map<String, Integer> map : maps) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    private void addLinesToMap(String line) {
        if (map.containsKey(line)) {
            map.put(line, map.get(line) + 1);
        } else {
            map.put(line, 1);
        }
    }

    public void getStringFromInputStream(InputStream is) {
        map = new LinkedHashMap<>();
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
        maps.add(map);
    }
}
/*string = System.Text.Encoding.UTF8.GetString(byteArray);
//                        System.Text.Encoding.UTF8.GetString(buf).TrimEnd('\0');
//                        string = new String(bufferToString, StandardCharsets.UTF_8);
                        string = new String(bufferToString, StandardCharsets.UTF_8).trim();*/
