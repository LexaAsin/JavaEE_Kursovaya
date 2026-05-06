package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionProperty {
    public static final String CONFIG_NAME = "config.properties";
    public static final Properties GLOBAL_CONFIG = new Properties();

    public ConnectionProperty() throws IOException {
        InputStream input = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(CONFIG_NAME);
        if (input == null) {
            throw new IOException("Файл " + CONFIG_NAME + " не найден в classpath");
        }
        GLOBAL_CONFIG.load(input);
    }

    public static String getProperty(String property) {
        return GLOBAL_CONFIG.getProperty(property);
    }
}