package com.hmkcode.spring.mvc;

import com.hmkcode.spring.mvc.model.PostgreSQLManager;

import java.util.*;

/**
 * Created by Serzh on 10/14/16.
 */
public class Test2 {
    static Map<String, Integer> map;

    public static void main(String[] args) throws Exception {
        map = new LinkedHashMap<>();
        createMap(map);

        PostgreSQLManager manager = new PostgreSQLManager();
        manager.insertResult2(map);

        LinkedHashMap<String, Integer> object = manager.getMapFromResult(10);
        object.forEach((k, v) -> System.out.println(k.toString() + " : " + v.toString()) );
    }

    private static Map<String, Integer> createMap(Map<String, Integer> map) {
        String string = "someString";
        for (int i = 0; i < 5; i++) {
            string += "a";
            map.put(string, i + 5);
        }
        return map;
    }
}
