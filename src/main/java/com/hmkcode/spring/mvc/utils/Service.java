package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;
import com.hmkcode.spring.mvc.result.JsonDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Serzh on 10/12/16.
 */
public class Service {
    private static List<Map<String, Integer>> maps = new LinkedList<>();
    static Map<String, Integer> map;

    public static void main(String[] args) throws IOException {
        Service service = new Service();
        PostgreSQLManager manager = new PostgreSQLManager();
        InputStream inputStream = manager.selectFile(16);

        service.getLinesAddToMap(inputStream);
        JsonDocument jsonDocument = new JsonDocument(map);
        String string = jsonDocument.toString();
        System.out.println(string);
    }

    private void addLinesToMap(String line) {
        if (map.containsKey(line)) {
            map.put(line, map.get(line) + 1);
        } else {
            map.put(line, 1);
        }
    }

    public void getLinesAddToMap(InputStream is) {
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

 /* for (Map<String, Integer> map : maps) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }*/
//        maps.forEach(map -> map.forEach((k, v) -> System.out.println("Key : " + k + " Value : " + v)));
