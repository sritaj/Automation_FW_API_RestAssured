import listeners.RetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reports.ExtentReportsImp;

import java.lang.reflect.Method;

public class BaseTest {

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        // Extent Report Initialization
        ExtentReportsImp.initializeReport();

        //Initializing Tests with Retry Analyzer Annotation
        for (ITestNGMethod method : context.getAllTestMethods()) {
            method.setRetryAnalyzerClass(RetryAnalyzer.class);
        }
    }

    @BeforeMethod
    public void setup(Method method) {
        // Extent Report Initialization
        String testName = method.getName();
        String testDescription = method.getAnnotation(Test.class).testName();
        ExtentReportsImp.startTestExecution(testName, testDescription);
    }

    @AfterMethod
    public void tearDown(ITestResult result, Method method) {
        ExtentReportsImp.addDetails(method);
        ExtentReportsImp.addCustomDetails(method);
        if (ITestResult.FAILURE == result.getStatus()) {
            RetryAnalyzer rerun = new RetryAnalyzer();
            rerun.retry(result);

        } else if (ITestResult.SUCCESS == result.getStatus()) {
            String testName = result.getName();
            ExtentReportsImp.passTest(testName);

        } else if (ITestResult.SKIP == result.getStatus()) {
            String testName = result.getName();
            ExtentReportsImp.skipTest(testName);
        }

    }

    @AfterSuite()
    public void afterSuite() {
        ExtentReportsImp.flushReports();
    }
}
