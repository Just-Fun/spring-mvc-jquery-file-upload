package com.hmkcode.spring.mvc.utils;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Serzh on 10/12/16.
 */
// TODO implement MapReduce
public class Service {
    private PostgreSQLManager manager; // TODO bean
    private ExecutorService executor;
    private Parser mapCreator;

    public Service() {
        executor = Executors.newFixedThreadPool(3);
        manager = new PostgreSQLManager();
        mapCreator = new Parser();
    }

    public Map<String, Integer> run(long session) {
        List<Integer> filesId = manager.selectIdBySession(session);

        selectFileById(filesId);
        System.out.println("After selectFileById");

        while (!executor.isTerminated()) {
            // wait until executing completed
        }
        return mapCreator.checkMaps();
    }

    private void selectFileById(List<Integer> filesId) {
        for (Integer id : filesId) {
            Runnable task = new FilesToMap(id);
            executor.execute(task);
        }
        executor.shutdown();
    }

    private class FilesToMap implements Runnable {
        private Integer id; // int?

        private FilesToMap(Integer id) {
            this.id = id;
        }

        @Override
        public void run() {
            InputStream inputStream = manager.selectFileById(id);
            mapCreator.createMapFromLines(inputStream);
        }
    }
}