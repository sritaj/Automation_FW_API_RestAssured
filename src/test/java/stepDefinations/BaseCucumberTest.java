package stepDefinations;

import enums.ReqResSpecs;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import utils.PropertiesFileImpl;

public class BaseCucumberTest {

    @Before
    public void setup() {
        RestAssured.baseURI = PropertiesFileImpl.getDataFromPropertyFile(ReqResSpecs.REQRESURI);
        RestAssured.basePath = PropertiesFileImpl.getDataFromPropertyFile(ReqResSpecs.REQRESBASEPATH);
    }
}
