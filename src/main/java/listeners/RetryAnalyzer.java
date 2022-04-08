package listeners;

import constants.FrameworkConstants;
import enums.ConfigProperties;
import enums.TestSpecs;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import reports.ExtentReportsImp;
import utilities.PropertiesFileImpl;

public final class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;

    private int maxAttempt = FrameworkConstants.getRetryCounts();

    @Override
    public boolean retry(ITestResult result) {
        if (PropertiesFileImpl.getDataFromPropertyFile(TestSpecs.RETRYFAILEDTEST).equalsIgnoreCase("yes")) {
            if (count < maxAttempt) {
                count++;
                return true;
            } else {
                String testName = result.getName();
                ExtentReportsImp.failTest(testName);
                ExtentReportsImp.failTestException(result.getThrowable());
            }
        } else {
            String testName = result.getName();
            ExtentReportsImp.failTest(testName);
            ExtentReportsImp.failTestException(result.getThrowable());
        }
        return false;
    }

}
