package com.hmkcode.spring.mvc.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class JsonDocument {
    private String json;
    private  Map<String, Integer> lines;

    public JsonDocument(Map<String, Integer> map) {
        lines = map;
    }

    @Override
    public String toString() {
        return lines.entrySet().stream()
                .map(e -> "{\n\"value\": \"" + e.getKey() + "\",\n\"count\": " + e.getValue() + "}")
                .collect(Collectors.joining(",\n", "[\n", "\n]"));
    }
}