package raiffeisen.bank.aval.file_operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public enum ConfigsSingleton implements Configs {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(ConfigsSingleton.class);
    private static Properties properties;

    public String getProperty(String propertyName) {
        if (properties == null) {
            synchronized (ConfigsSingleton.class) {
                if (properties == null) {
                    properties = new Properties();
                    loadPropertiesFromFiles();
                }
            }
        }
        return properties.getProperty(propertyName);
    }

    public void addProperty(String key, String value) {
        if ((getProperty(key) != null)) {
            throw new IllegalArgumentException("The key is already in the properties.");
        } else properties.setProperty(key, value);
    }

    private static void loadPropertiesFromFiles() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream startUp = classLoader.getResourceAsStream("application.properties");
            properties.load(startUp);
            InputStream input = new FileInputStream
                    (System.getProperty("user.home")
                            + properties.getProperty("app.ext.properties.path"));
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.debug("test.java.raiffeisen_bank_aval.file_operations.ConfigLoader.loadConfigs()", e);
        }
    }
}
