package utilities;

import constants.FrameworkConstants;
import enums.ConfigProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class PropertiesFileImpl {

    private PropertiesFileImpl() {
    }

    private static Properties prop = new Properties();
    private static final HashMap<String, String> CONFIGMAP = new HashMap<>();

    //defining the properties file load in static block so that it can be initialized once
    static {
        try {
            File f = new File(FrameworkConstants.getPropertiesFilePath());
            FileInputStream fis = new FileInputStream(f);
            prop.load(fis);

            // Creating Hashmap, relevant when properties file is read multiple times and needs to be faster then the normal hashtable approach
            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }

            //prop.entrySet().forEach(entry -> CONFIGMAP.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        } catch (IOException e) {
            System.err.println("Properties file couldn't be found" + e.getMessage());
        }

    }

    public static String getDataFromPropertyFile(ConfigProperties key) {
        String property = null;
        try {
            if (Objects.isNull(CONFIGMAP.get(key))) {
                property = CONFIGMAP.get(key.name().toLowerCase());
            }
        } catch (NullPointerException e) {
            System.err.println("Null pointer exception" + e.getMessage());
        }
        if (property == null) {
            throw new NullPointerException("Specified Key -> '" + property + "' is not found in config properties");
        }
        return property;
    }

}
