import annotations.CustomFrameworkAnnotations;
import com.github.javafaker.Faker;
import enums.SparksSpecs;
import enums.TestCaseType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.JsonPathImpl;
import utils.PropertiesFileImpl;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class StripeAPITest {

    RequestSpecification specs;
    Response response;
    String resp;
    String customer_id;

    final String errorMessageForMissingAPIKey = "You did not provide an API key. You need to provide your API key in the Authorization header, using Bearer auth (e.g. 'Authorization: Bearer YOUR_SECRET_KEY'). See https://stripe.com/docs/api#authentication for details, or we can help at https://support.stripe.com/.";
    final String errorMessageTypeForMissingAPIKey = "invalid_request_error";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPEBASEURI);
        RestAssured.basePath = PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPEBASEPATH);
        specs = given().auth().basic(PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPESECRETKEY), "");
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.FUNCTIONAL)
    @Ignore
    @Test(testName = "Validate customer list when no customers are present")
    public void getCustomerListWhenCustomersAreNotCreated() {

        response = specs.get(PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();

        resp = response.asString();
        JsonPath js = JsonPathImpl.rawToJSON(resp);
        int datasize = js.getList("data").size();

        response.then().assertThat().statusCode(200);
        Assert.assertEquals(datasize, 0);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate creation of Customer without any body/inputs", priority = 0)
    public void createCustomerWithoutAnyBody() {
        response = specs.contentType(ContentType.ANY)
                .when().post(PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();
        resp = response.asString();

        String id = JsonPathImpl.extractValueFromResponse(resp, "id");
        response.then().assertThat().statusCode(200);
        Assert.assertNotNull(id);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate creation of Customer with Form parameters", priority = 1)
    public void createCustomersWithFormParameters() {
        Faker fs = new Faker();
        String email = fs.internet().emailAddress();
        String name = fs.name().firstName();
        response = specs.config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("*/*", ContentType.TEXT)))
                .formParam("name", name).formParam("email", email)
                .when().post(PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPECUSTOMERAPIENDPOINT));
        resp = response.asString();

        customer_id = JsonPathImpl.extractValueFromResponse(resp, "id");
        String actualName = JsonPathImpl.extractValueFromResponse(resp, "name");
        String actualEmailID = JsonPathImpl.extractValueFromResponse(resp, "email");

        response.then().assertThat().statusCode(200);
        Assert.assertEquals(actualName, name);
        Assert.assertEquals(actualEmailID, email);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate creation of Customer without authentication", priority = 3)
    public void createCustomerWithoutAuthentication() {
        response = given()
                .when().post(PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();
        resp = response.asString();

        response.then().assertThat().statusCode(401);
        String errorMessage = JsonPathImpl.extractValueFromResponse(resp, "error.message");
        String errorType = JsonPathImpl.extractValueFromResponse(resp, "error.type");

        Assert.assertEquals(errorMessage, errorMessageForMissingAPIKey);
        Assert.assertEquals(errorType, errorMessageTypeForMissingAPIKey);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate customer list", priority = 4)
    public void getCustomerList() {

        response = specs.queryParam("limit", "3").get(PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        resp = response.asString();
        JsonPath js = JsonPathImpl.rawToJSON(resp);
        int listSize = js.getList("data").size();
        Assert.assertEquals(listSize, 3);

    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.E2E)
    @Test(testName = "Validate fetching of Customer based on ID", dependsOnMethods = {"createCustomersWithFormParameters"}, priority = 5)
    public void getCustomerBasedOnID() {

        response = specs.pathParam("id", customer_id)
                .when().get(PropertiesFileImpl.getDataFromPropertyFile(SparksSpecs.STRIPECUSTOMERAPIENDPOINT) + "/:{id}");
        response.prettyPrint();
        resp = response.asString();

    }

}
