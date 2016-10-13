package com.hmkcode.spring.mvc.result;

import java.util.Map;
import java.util.stream.Collectors;

public class JsonDocument {
    private  Map<String, Integer> map;

    public JsonDocument(Map<String, Integer> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return map.entrySet().stream()
                .map(e -> String.format("{\n\"value\": \"%s\",\n\"count\": %d\n}", e.getKey(), e.getValue()))
                .collect(Collectors.joining(",\n", "[\n", "\n]"));
    }
}