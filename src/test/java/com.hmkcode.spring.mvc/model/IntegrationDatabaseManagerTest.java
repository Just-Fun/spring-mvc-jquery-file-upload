package com.hmkcode.spring.mvc.model;

import com.hmkcode.spring.mvc.model.DatabaseManager;
import com.hmkcode.spring.mvc.utils.Setup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Serzh on 10/18/16.
 */
public class IntegrationDatabaseManagerTest {
    private static Setup setup;
    private static DatabaseManager manager;

    @BeforeClass
    public static void setup() {
        setup = new Setup();
        manager = setup.createData();
    }

    @AfterClass
    public static void dropDatabase() {
        setup.dropData();
    }

    @Test
    public void testGetMapFromResultBySession() throws Exception {
        Map<String, Integer> result = manager.getMapFromResultBySession(setup.getSesson1());
        Map<String, Integer> map = new TreeMap<>(result);

        assertEquals("{First File!!!=1, First row=1, Second row=1}",
                result.toString());
    }

    @Test
    public void testGetMapFromResultById() throws Exception {
        Map<String, Integer> result = manager.getMapFromResultById(1);
        Map<String, Integer> map = new TreeMap<>(result);

        assertEquals("{First File!!!=1, First row=1, Second row=1}",
                result.toString());
    }

}