package utils;

import constants.FrameworkConstants;
import enums.ConfigProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

/**
 * PropertiesFileImpl class to load PropertiesFile and method to read specified key value from it
 */
public final class PropertiesFileImpl {

    private PropertiesFileImpl() {
    }

    private static Properties prop = new Properties();
    private static final HashMap<String, String> CONFIGMAP = new HashMap<>();

    static {
        try {
            File file = new File(FrameworkConstants.getPropertiesFilePath());
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);

            prop.forEach((key, value) -> CONFIGMAP.put(String.valueOf(key), String.valueOf(value)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to retrieve the key value from properties file
     *
     * @param propertyKey - The Enum for which the value needs to be extracted
     * @return value of the specified propertyKey
     */
    public static String getDataFromPropertyFile(ConfigProperties propertyKey) {
        try {
            if (Objects.isNull(propertyKey) || Objects.isNull(CONFIGMAP.get(propertyKey.toString().toLowerCase()))) {
                System.err.println("Specified Key -> '" + propertyKey + "' is not found in config properties");
            }
        } catch (NullPointerException e) {
            System.err.println("Null pointer exception" + e.getMessage());
        }
        return CONFIGMAP.get(propertyKey.toString().toLowerCase());
    }
}
