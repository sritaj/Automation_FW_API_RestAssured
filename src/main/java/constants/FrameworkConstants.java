package constants;

public class FrameworkConstants {

    private FrameworkConstants() {
    }

    private static final String RESOURCEPATH = System.getProperty("user.dir");
    private static final String PROPERTIESFILEPATH = RESOURCEPATH + "/src/test/resources/config.properties";

    public static String getPropertiesFilePath() {
        return PROPERTIESFILEPATH;
    }
}
