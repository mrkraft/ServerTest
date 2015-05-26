package rio.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This is util class, that reads project properties from config.properties file
 */
public class Prop {

    private static final Logger logger = LoggerFactory.getLogger(Prop.class);

    private static final String PROP_FILE_NAME = "config.properties";

    private static volatile Properties properties;

    private Prop() {
    }

    private static Properties getInstance() {
        if (properties == null) {
            synchronized (Prop.class) {
                properties = new Properties();
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                InputStream stream = loader.getResourceAsStream(PROP_FILE_NAME);
                try {
                    properties.load(stream);
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return properties;
    }

    public static String getProp(String key) {
        return getInstance().getProperty(key);
    }
}
