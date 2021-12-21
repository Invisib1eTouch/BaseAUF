package core;

import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    protected static Properties properties;

    static {
        properties = new Properties();

        try {
            properties.load(PropertyReader.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getBrowserName() {
        return properties.getProperty("browser");
    }

}
