package com.hmkcode.spring.mvc.service;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by Serzh on 10/21/16.
 */
public interface Parser {
    void createMapFromFile(InputStream is);

    Map<String, Integer> getResult();
}
