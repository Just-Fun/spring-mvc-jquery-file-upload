package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;
import com.hmkcode.spring.mvc.result.JsonDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Serzh on 10/12/16.
 */
public class Service {
    private List<Map<String, Integer>> maps;
    private Map<String, Integer> map;
    private Map<String, Integer> result;
    private PostgreSQLManager manager;
    private ExecutorService executor;

    public static void main(String[] args) throws IOException {
        String result = new Service().run(1476441073232L);
        System.out.println(result);
    }

    public Service() {
        executor = Executors.newFixedThreadPool(3);
        maps = new ArrayList<>();
        manager = new PostgreSQLManager();
    }

    public String run(long session) {
        List<Integer> selectIdFilesFromSession = manager.selectFiles(session);

        selectFileById(selectIdFilesFromSession);
        System.out.println("After selectFileById");

        while (!executor.isTerminated()) {
            // just wait
        }
        checkMaps();

        return resultToJson();
    }

    private void selectFileById(List<Integer> selectIdFilesFromSession) {
        for (Integer id : selectIdFilesFromSession) {
            Runnable task = new FilesToMap(id);
            executor.execute(task);
        }
        executor.shutdown();
    }

    private class FilesToMap implements Runnable {
        private Integer id;

        private FilesToMap(Integer id) {
            this.id = id;
        }

        @Override
        public void run() {
            filesLineToMap(id);
        }
    }

    private void filesLineToMap(Integer id) {
        InputStream inputStream = manager.selectFile(id);
        createMapFromLines(inputStream);
    }

    private String resultToJson() {
        JsonDocument jsonDocument = new JsonDocument(result);
        return jsonDocument.toString();
    }

    private void checkMaps() {
        System.out.println("inside checkMaps(), maps.size(): " + maps.size());
        if (maps.size() > 1) {
            result = concatMaps(maps);
        } else {
//            if (maps.size() == 0) { // заглушка
//                result = new LinkedHashMap<>();
//            } else {
                result = maps.get(0);
//            }
        }
    }

    private void addLinesToMap(String line) {
        if (map.containsKey(line)) {
            map.put(line, map.get(line) + 1);
        } else {
            map.put(line, 1);
        }
    }

    //TODO optimized algorithm
    public Map<String, Integer> concatMaps(List<Map<String, Integer>> maps) {
        Map<String, Integer> result = maps.get(0);

        for (int i = 1; i < maps.size(); i++) {
            Map<String, Integer> mapConcat = maps.get(i);
            concatTwoMaps(result, mapConcat);
        }
        return result;
    }

    private Map<String, Integer> concatTwoMaps(Map<String, Integer> result, Map<String, Integer> map2) {
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

    private void createMapFromLines(InputStream is) {
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
        mapAddToMaps();
    }

    private synchronized void mapAddToMaps() {
        maps.add(map);
    } // надо ли?
}