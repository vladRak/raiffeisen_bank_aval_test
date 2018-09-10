package raiffeisen.bank.aval.file_operations;

import java.util.Properties;

public interface Configs {
    String getProperty(String propertyName);
    void addProperty(String key, String value);
}
