package ru.ozon.autotests.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

    private final Properties properties = new Properties();

    private static TestProperties INSTANCE = null;

    private TestProperties(){
        System.setProperty("environment", "environment");
        try {
            properties.load(new FileInputStream(new File("./application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TestProperties getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestProperties();
        }
        return INSTANCE;
    }
    public Properties getProperties(){
        return properties;
    }
}
