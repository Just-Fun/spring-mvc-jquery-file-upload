package com.hmkcode.spring.mvc.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Serzh on 10/12/16.
 */
public class MapToJson {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("name", 44);
        map.put("age", 29);

        String json = toJson(map);
        System.out.println(json);

        String json2 = toJsonOneRow(map);
        System.out.println(json2);

        String path = "src/main/temp/jsonTemp.txt";
        JsonToFile(path, map);
    }

    private static void JsonToFile(String path, Map<String, Integer> map) {
        ObjectMapper mapper = new ObjectMapper();
        // write JSON to a file
        try {
            mapper.writeValue(new File(path), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void toJson5(OutputStream out, Map<String, Integer> map) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(out, map);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String toJson(Map<String, Integer> map) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static String toJsonOneRow(Map<String, Integer> map) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

}