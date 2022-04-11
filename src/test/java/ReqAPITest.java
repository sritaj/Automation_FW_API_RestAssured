import base.BaseTest;
import dto.UserData;
import enums.ReqResSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import listeners.CustomAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pojo.UserPojo;
import utilities.JsonPathImpl;
import utilities.PropertiesFileImpl;
import utilities.ReqSpecsImpl;

import static io.restassured.RestAssured.given;

public class ReqAPITest extends BaseTest {

    RequestSpecification specs;
    Response response;
    UserData udata;
    UserPojo upojo;
    String resp;

    final String logFileName = "ReqAPITests";

    @BeforeTest
    public void initialiseSetup() {
        ReqSpecsImpl reqSpecs = new ReqSpecsImpl();
        specs = reqSpecs.setRequestSpecs(PropertiesFileImpl.getDataFromPropertyFile(ReqResSpecs.REQRESURI),
                PropertiesFileImpl.getDataFromPropertyFile(ReqResSpecs.REQRESBASEPATH), logFileName);

    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate getting list of users based on Query params")
    public void getListOfUsers() {
        response = given(specs).queryParam("page", "2")
                .when().get("/users");

        response.prettyPrint();
        resp = response.asString();
        String pageCount = JsonPathImpl.extractValueFromResponse(resp, "page");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(pageCount, "2");

    }

    @CustomAnnotations(testCaseType = "Integration")
    @Test(testName = "Validate creation of user")
    public void createAnUser() {
        udata = new UserData();
        upojo = udata.createUser();
        response = given(specs).body(upojo)
                .when().post("/users");
        resp = response.asString();
        String id = JsonPathImpl.extractValueFromResponse(resp, "id");
        Assert.assertNotNull(id);
        Assert.assertEquals(response.getStatusCode(), 201);
        response.prettyPrint();
        System.out.println(id);
    }

}
