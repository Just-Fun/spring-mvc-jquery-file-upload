package com.hmkcode.spring.mvc.service;

import com.hmkcode.spring.mvc.model.DatabaseManager;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Serzh on 10/12/16.
 */
public class ServiceImpl implements Service {
    private DatabaseManager manager;
    private ExecutorService executor;
    private Parser parser;

    public ServiceImpl(DatabaseManager manager) {
        this.manager = manager;
        executor = Executors.newFixedThreadPool(3);
        parser = new ParserImpl();
    }

    @Override
    public Map<String, Integer> run(long sessionTime) {
        List<Integer> filesId = manager.selectIdBySession(sessionTime);

        selectFilesFromSession(filesId);

        while (!executor.isTerminated()) {
            // wait until executing completed
        }
        return parser.getResult();
    }

    private void selectFilesFromSession(List<Integer> filesId) {
        for (Integer id : filesId) {
            Runnable task = new FilesToMap(id);
            executor.execute(task);
        }
        executor.shutdown();
    }

    private class FilesToMap implements Runnable {
        private int id;

        private FilesToMap(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            InputStream inputStream = manager.selectFileById(id);
            parser.createMapFromFile(inputStream);
        }
    }
}