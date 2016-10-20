package com.hmkcode.spring.mvc.inegration;

import com.hmkcode.spring.mvc.service.Service;
import com.hmkcode.spring.mvc.utils.Setup;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by Serzh on 10/18/16.
 */
public class ServiceTest {
    private static Setup setup;
    private Service service;

    @BeforeClass
    public static void setup() {
        setup = new Setup();
        setup.createData();
    }

    @AfterClass
    public static void dropDatabase() {
        setup.dropData();
    }

    @Test
    public void testServiceOneFile() throws Exception {
        service = new Service();
        Map<String, Integer> result = service.run(setup.getSesson1());

        assertEquals("{First File!!!=1, First row=1, Second row=1}", result.toString());
    }

    @Test
    public void testServiceTwoFiles() throws Exception {
        service = new Service();
        Map<String, Integer> result = service.run(setup.getSesson2());
        Map<String, Integer> map = new TreeMap<>(result);

        assertEquals("{First row=2, Second File!!!=1, Second row=2, Third File!!!=1, Third row=1}",
                map.toString());
    }

}