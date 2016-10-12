package com.hmkcode.spring.mvc.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonDocument {
    private final String name;
    private final Map<String, Integer> fields = new LinkedHashMap<>();

    public JsonDocument(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addField(String name, Integer value) {
        fields.put(name, value);
    }

    @Override
    public String toString() {
        return fields.entrySet().stream()
                .map(e -> "'value': " + e.getKey() + ",\ncount: " + e.getValue())
                .collect(Collectors.joining(",\n", "{\n", "\n}"));
    }
}
/*[
{
“value”: “xyz”,
“count”: 10
},
{...}
]*/
