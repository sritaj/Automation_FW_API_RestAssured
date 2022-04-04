package cucumber.Options;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/main/java/features", glue = {"stepDefinations"})
public class TestRunner extends AbstractTestNGCucumberTests {

}
