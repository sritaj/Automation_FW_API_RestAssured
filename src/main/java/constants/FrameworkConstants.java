package constants;

import enums.ExtentReportSpecs;
import utilities.PropertiesFileImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FrameworkConstants {

    private FrameworkConstants() {
    }

    private static final String RESOURCEPATH = System.getProperty("user.dir");
    private static final String PROPERTIESFILEPATH = RESOURCEPATH + "/src/test/resources/properties/config.properties";
    private static final String EXTENTREPORTSPATH = RESOURCEPATH + "/test-reports/";
    private static final String JSONFILESPATH = RESOURCEPATH + "/src/test/resources/jsonfiles";
    public static final int RETRYCOUNTS = 1;

    public static String getPropertiesFilePath() {
        return PROPERTIESFILEPATH;
    }

    public static String getExtentReportPath() {
        if (PropertiesFileImpl.getDataFromPropertyFile(ExtentReportSpecs.OVERRIDEREPORTS).equalsIgnoreCase("yes")) {
            return EXTENTREPORTSPATH;
        }
        return EXTENTREPORTSPATH + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "/";
    }

    public static int getRetryCounts() {
        return RETRYCOUNTS;
    }

    public static String getJsonFilePath() {
        return JSONFILESPATH;
    }
}
