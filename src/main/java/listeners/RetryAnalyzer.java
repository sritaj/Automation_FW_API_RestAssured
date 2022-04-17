package listeners;

import constants.FrameworkConstants;
import enums.TestSpecs;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utilities.PropertiesFileImpl;

/**
 * RetryAnalyzer class to implement Retry runs for failed Tests
 */
public final class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    private final int maxAttempt = FrameworkConstants.getRetryCounts();

    /**
     * IRetryAnalyzer Overriden Method to check for Retry count and rerun test cases accordingly
     *
     * @param result - ITestResult
     */
    @Override
    public boolean retry(ITestResult result) {
        if ((PropertiesFileImpl.getDataFromPropertyFile(TestSpecs.RETRYFAILEDTEST).equalsIgnoreCase("yes"))
                && (retryCount < maxAttempt)) {
            retryCount++;

            return true;
        }
        return false;
    }

}
