package com.hmkcode.spring.mvc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Serzh on 10/18/16.
 */
// TODO optimization algorithm (check if MapReduce will be faster)
public class ParserImpl implements Parser {

    private List<Map<String, Integer>> maps;
    private Map<String, Integer> result;

    public ParserImpl() {
        maps = new ArrayList<>();
    }

    @Override
    public void createMapFromFile(InputStream is) {
        Map<String, Integer> map = new LinkedHashMap<>();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    linesToMap(map, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addMapToMaps(map);
    }

    private void linesToMap(Map<String, Integer> map, String line) {
        if (map.containsKey(line)) {
            map.put(line, map.get(line) + 1);
        } else {
            map.put(line, 1);
        }
    }

    private synchronized void addMapToMaps(Map<String, Integer> map) {
        maps.add(map);
    }

    @Override
    public Map<String, Integer> getResult() {
        if (maps.size() > 1) {
            result = concatMaps(maps);
        } else {
            result = maps.get(0);
        }
        return SortByValue.sortByValue(result);
    }

    private Map<String, Integer> concatMaps(List<Map<String, Integer>> maps) {
        Map<String, Integer> result = maps.get(0);

        for (int i = 1; i < maps.size(); i++) {
            Map<String, Integer> mapConcat = maps.get(i);
            concatTwoMaps(result, mapConcat);
        }
        return result;
    }

    private Map<String, Integer> concatTwoMaps(Map<String, Integer> result, Map<String, Integer> map2) {
        // short, but very slow
//        map2.forEach((k, v) -> result.merge(k, v, Integer::sum));

        for (Map.Entry<String, Integer> entry : map2.entrySet()) {
            String line = entry.getKey();
            if (result.containsKey(line)) {
                result.put(line, result.get(line) + entry.getValue());
            } else {
                result.put(line, entry.getValue());
            }
        }
        return result;
    }
}
