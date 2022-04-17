package reports;

import com.aventstack.extentreports.ExtentTest;

/**
 * ExtentReportManager class to implement Thread Safety for Extent Report Instances for parallel executions
 */
public class ExtentReportManager {

    private ExtentReportManager() {
    }

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static void setTest(ExtentTest testRef) {
        test.set(testRef);
    }

    static ExtentTest getTest() {
        return test.get();
    }

    static void removeTest(ExtentTest testRef) {
        test.remove();
    }
}
