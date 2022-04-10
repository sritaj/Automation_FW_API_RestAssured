package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "", features = "src/main/resources/features",
        glue = {"stepDefinations"}, dryRun = false,
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"})
public class CucumberRunner extends AbstractTestNGCucumberTests {

}
