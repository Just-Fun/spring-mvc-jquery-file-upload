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

        long begin = System.currentTimeMillis();
        Service service = new Service();
        PostgreSQLManager manager = new PostgreSQLManager();

        List<Integer> selectIdFilesFromSession = manager.selectFiles(1476441073232L);
        for (Integer id : selectIdFilesFromSession) {
            InputStream inputStream = manager.selectFile(id);
            service.getLinesAndAddToMap(inputStream);
        }

        if (maps.size() > 1) {
            concatMaps(maps);
        }

        for (Map<String, Integer> map : maps) {
            JsonDocument jsonDocument = new JsonDocument(map);
            String string = jsonDocument.toString();
            System.out.println(string);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    //TODO optimized algorithm
    public static Map<String, Integer> concatMaps(List<Map<String, Integer>> maps) {
        Map<String, Integer> result = maps.get(0);

        for (int i = 1; i < maps.size(); i++) {
            Map<String, Integer> mapConcat = maps.get(i);
            Map<String, Integer> concatTwoMaps = concatTwoMaps(result, mapConcat);
        }
        return result;
    }

    private static Map<String, Integer> concatTwoMaps(Map<String, Integer> map1, Map<String, Integer> map2) {
        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
            String line = entry.getKey();
            if (map1.containsKey(line)) {
                map1.put(line, map1.get(line) + entry.getValue());
            } else {
                map1.put(line, entry.getValue());
            }
        }
        return map1;
    }

    private void addLinesToMap(String line) {
        if (map.containsKey(line)) {
            map.put(line, map.get(line) + 1);
        } else {
            map.put(line, 1);
        }
    }

    public void getLinesAndAddToMap(InputStream is) {
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
