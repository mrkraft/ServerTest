package rio.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    public Properties getProps() {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("config.properties");
        try {
            prop.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
}
