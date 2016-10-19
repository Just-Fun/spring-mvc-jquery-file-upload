package com.hmkcode.spring.mvc.inegration;

import com.hmkcode.spring.mvc.utils.Setup;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Serzh on 10/18/16.
 */
public class ServiceTest {

    @BeforeClass
    public static void setup() {
        Setup setup = new Setup();
        setup.createData();
       /* manager = new PostgreSQLManager();
        setup.setupData(manager, DATABASE);*/
    }
    @Test
    public void run() throws Exception {

    }

}