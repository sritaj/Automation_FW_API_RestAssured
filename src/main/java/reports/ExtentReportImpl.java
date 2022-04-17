package reports;

import annotations.CustomFrameworkAnnotations;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.FrameworkConstants;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * ExtentReportImpl class to implement Extent Report initialisation, teardown, and relevant methods
 */
public final class ExtentReportImpl {

    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private static Markup m;
    private final static String testInfo = "TEST CASE: - ";

    private ExtentReportImpl() {
    }

    /**
     * Method to initialize Extent Report
     */
    public static void initializeReport() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportPath() + "AutomationReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("API Automation");
            extent.attachReporter(spark);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("OS Version", System.getProperty("os.version"));
            extent.setSystemInfo("Java Version", System.getProperty("java.runtime.version"));
        }
    }

    /**
     * Method to set the Extent Report on Test run
     *
     * @param testDescription - Test Description from TestNG to append to report
     * @param testName        - Test Name to append to report
     */
    public static void startTestExecution(String testDescription, String testName) {
        ExtentReportManager.setTest(extent.createTest(testDescription, testName));
    }

    /**
     * Method to mark a Test as pass with required info and screenshot
     *
     * @param testName - Test Name to append to info
     */
    public static void passTest(String testName) {
        String passText = "<b>" + testInfo + testName + " PASSED" + "</b>";
        m = MarkupHelper.createLabel(passText, ExtentColor.GREEN);
        ExtentReportManager.getTest().pass(m);
    }

    /**
     * Method to mark a Test as skip with required info and screenshot
     *
     * @param testName - Test Name to append to info
     */
    public static void skipTest(String testName) {
        String skipTest = "<b>" + testInfo + testName + " SKIPPED" + "</b>";
        m = MarkupHelper.createLabel(skipTest, ExtentColor.GREY);
        ExtentReportManager.getTest().skip(m);
    }

    /**
     * Method to mark a Test as fail with required info and screenshot
     *
     * @param testName      - Test Name to append to info
     * @param failureInfo   - Failure Info from TestNG
     * @param throwableInfo - Errors/Exceptions info
     */
    public static void failTest(String testName, String failureInfo, Throwable throwableInfo) {
        String failText = "<b>" + testInfo + testName + " FAILED" + "</b>";
        m = MarkupHelper.createLabel(failText, ExtentColor.RED);
        ExtentReportManager.getTest().fail(m);
        logSteps(failureInfo);
        logFailureInfo(throwableInfo);
    }

    /**
     * Method to log the steps
     *
     * @param record - Pass the steps info
     */
    public static void logSteps(String record) {
        ExtentReportManager.getTest().info(record);
    }

    /**
     * Method to get the failure info for Failed Test Cases
     *
     * @param throwable - Pass the failure info
     */
    public static void logFailureInfo(Throwable throwable) {
        ExtentReportManager.getTest().fail(throwable);
    }

    /**
     * Method to flush Extent Report
     */
    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
        }
    }

    /**
     * Method to get details from the Test to append to the Extent Reports
     *
     * @param method - Java Reflection method to access the method/test to retrieve details
     */
    public static void addDetails(Method method) {
        ExtentReportManager.getTest()
                .assignCategory(method.getAnnotation(Test.class).groups())
                .assignCategory(String.valueOf(method.getAnnotation(CustomFrameworkAnnotations.class).testCaseType()));
    }
}
