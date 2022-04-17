package constants;

import enums.ExtentReportSpecs;
import utilities.PropertiesFileImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FrameworkConstants class to store paths and common properties
 */
public class FrameworkConstants {

    private FrameworkConstants() {
    }

    private static final String RESOURCEPATH = System.getProperty("user.dir");
    private static final String PROPERTIESFILEPATH = RESOURCEPATH + "/src/test/resources/properties/config.properties";
    private static final String EXTENTREPORTSPATH = RESOURCEPATH + "/test-reports/";
    private static final String JSONFILESPATH = RESOURCEPATH + "/src/test/resources/jsonfiles";
    private static final String RESTASSUREDLOGSPATH = RESOURCEPATH + "/test-logs/";
    public static final int RETRYCOUNTS = 1;

    /**
     * Method to get Properties file path
     *
     * @return String - Properties file path
     */
    public static String getPropertiesFilePath() {
        return PROPERTIESFILEPATH;
    }

    /**
     * Method to get Extent Report path
     *
     * @return String - Extent report path
     */
    public static String getExtentReportPath() {
        if (PropertiesFileImpl.getDataFromPropertyFile(ExtentReportSpecs.OVERRIDEREPORTS).equalsIgnoreCase("yes")) {
            return EXTENTREPORTSPATH;
        }
        return EXTENTREPORTSPATH + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "/";
    }

    /**
     * Method to get retry counts
     *
     * @return int - retry counts
     */
    public static int getRetryCounts() {
        return RETRYCOUNTS;
    }

    /**
     * Method to get Json file path
     *
     * @return String - Json file path
     */
    public static String getJsonFilePath() {
        return JSONFILESPATH;
    }

    /**
     * Method to get Rest assured logs file path
     *
     * @return String - Rest assured logs file path
     */
    public static String getRestassuredLogsPath() {
        return RESTASSUREDLOGSPATH;
    }
}
