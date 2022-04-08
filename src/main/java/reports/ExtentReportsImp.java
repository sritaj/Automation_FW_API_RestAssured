package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.FrameworkConstants;
import listeners.CustomAnnotations;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Objects;


public final class ExtentReportsImp {

    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private static Markup m;

    private ExtentReportsImp() {
    }


    public static void initializeReport() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportPath() + "AutomationReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("API Automation");
            extent.attachReporter(spark);
        }
    }

    public static void startTestExecution(String testName, String description) {
        ExtentReportManager.setTest(extent.createTest(testName, description));
    }

    public static void passTest(String testName) {
        String passText = "<b>" + "TEST CASE:- " + testName + " PASSED" + "</b>";
        m = MarkupHelper.createLabel(passText, ExtentColor.GREEN);
        ExtentReportManager.getTest().pass(m);
    }

    public static void skipTest(String testName) {
        String skipTest = "<b>" + "TEST CASE:- " + testName + " SKIPPED" + "</b>";
        m = MarkupHelper.createLabel(skipTest, ExtentColor.GREY);
        ExtentReportManager.getTest().skip(m);
    }

    public static void failTest(String testName) {
        String failText = "<b>" + "TEST CASE:- " + testName + " FAILED" + "</b>";
        m = MarkupHelper.createLabel(failText, ExtentColor.RED);
        ExtentReportManager.getTest().fail(m);
    }

    public static void failTestException(Throwable throwable) {
        ExtentReportManager.getTest().fail(throwable);
    }

    public static void logSteps(String record) {
        ExtentReportManager.getTest().info(record);
    }

    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
        }
    }

    public static void addDetails(Method method) {
        ExtentReportManager.getTest().assignCategory(method.getAnnotation(Test.class).groups())
                .assignCategory(method.getAnnotation(Test.class).suiteName());
    }

    public static void addCustomDetails(Method method) {
        try {
            ExtentReportManager.getTest().assignCategory(method.getAnnotation(CustomAnnotations.class).testCaseType());
        } catch (NullPointerException e) {
            ExtentReportsImp.logSteps("Error " + e.getMessage());
        }

    }
}
