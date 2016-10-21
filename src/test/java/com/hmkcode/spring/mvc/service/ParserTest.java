package com.hmkcode.spring.mvc.service;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Serzh on 10/18/16.
 */
public class ParserTest {

    private Map<String, Integer> map;

    private final String FILE1 = "src/test/resources/file1.txt";
    private final String FILE2 = "src/test/resources/file2.txt";
    private final String FILE3 = "src/test/resources/file3.txt";
    private final String FILE4 = "src/test/resources/file4.txt";

    private Parser parser;

    @Before
    public  void setup() {
        parser = new ParserFirst();
    }

    @Test
    public void createMapFromOneFile() throws Exception {
        mapFromOneFile();

        parser.createMapFromFile(getFile(FILE1));
        Map<String, Integer> result = parser.getResult();

        assertEquals(map, result);
    }

    @Test
    public void createMapFromTwoFiles() throws Exception {
        mapFromTwoFiles();

        parser.createMapFromFile(getFile(FILE1));
        parser.createMapFromFile(getFile(FILE2));
        Map<String, Integer> result = parser.getResult();

        assertEquals(map, result);
    }

    @Test
    public void createMapFromFourFile() throws Exception {
        mapFromFourFiles();

        parser.createMapFromFile(getFile(FILE1));
        parser.createMapFromFile(getFile(FILE2));
        parser.createMapFromFile(getFile(FILE3));
        parser.createMapFromFile(getFile(FILE4));
        Map<String, Integer> result = parser.getResult();

        assertEquals(map, result);
    }

    @Test
    public void createMapFromLotsOfFiles() throws Exception {
        for (int i = 0; i < 100; i++) {
            InputStream inputStream = getFile(FILE1);
            parser.createMapFromFile(inputStream);
        }
        Map<String, Integer> result = parser.getResult();
        assertEquals("{First File!!!=100, First row=100, Second row=100}", result.toString());
    }


    private void mapFromOneFile() {
        map = new LinkedHashMap<>();
        map.put("First File!!!", 1);
        map.put("First row", 1);
        map.put("Second row", 1);
    }

    private void mapFromTwoFiles() {
        map = new LinkedHashMap<>();
        map.put("First File!!!", 1);
        map.put("Second File!!!", 1);
        map.put("First row", 2);
        map.put("Second row", 2);
    }

    private void mapFromFourFiles() {
        map = new LinkedHashMap<>();
        map.put("First File!!!", 1);
        map.put("First row", 4);
        map.put("Second row", 4);
        map.put("Second File!!!", 1);
        map.put("Third File!!!", 1);
        map.put("Third row", 2);
        map.put("Fourth File!!!", 1);
        map.put("Fourth row", 1);
    }

    public InputStream getFile(String path) {
        File initialFile = new File(path);
        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return targetStream;
    }
}