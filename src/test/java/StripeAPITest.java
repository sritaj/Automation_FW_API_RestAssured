import com.github.javafaker.Faker;
import enums.ConfigProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utilities.JsonPathImpl;
import utilities.PropertiesFileImpl;

import static io.restassured.RestAssured.given;

public class StripeAPITest {

    RequestSpecification specs;
    Response response;
    String resp;
    String customer_id;
    Faker fs = new Faker();
    String email = fs.internet().emailAddress();
    String name = fs.name().firstName();

    final String errorMessageForMissingAPIKey = "You did not provide an API key. You need to provide your API key in the Authorization header, using Bearer auth (e.g. 'Authorization: Bearer YOUR_SECRET_KEY'). See https://stripe.com/docs/api#authentication for details, or we can help at https://support.stripe.com/.";
    final String errorMessageTypeForMissingAPIKey = "invalid_request_error";

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPEBASEURI);
        RestAssured.basePath = PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPEBASEPATH);
        specs = given().auth().basic(PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPESECRETKEY), "");
    }

    @Ignore
    @Test(testName = "Validate customer list when no customers are present")
    public void getCustomerListWhenCustomersAreNotCreated() {

        response = specs.get(PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();

        resp = response.asString();
        JsonPath js = JsonPathImpl.rawToJSON(resp);
        int datasize = js.getList("data").size();

        response.then().assertThat().statusCode(200);
        Assert.assertEquals(datasize, 0);
    }

    @Test(testName = "Validate creation of Customer without any body/inputs")
    public void createCustomerWithoutAnyBody() {
        response = specs.contentType(ContentType.ANY)
                .when().post(PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();
        resp = response.asString();

        String id = JsonPathImpl.extractValueFromResponse(resp, "id");
        response.then().assertThat().statusCode(200);
        Assert.assertNotNull(id);
    }

    @Test(testName = "Validate creation of Customer with Form parameters")
    public void createCustomersWithFormParameters() {
        response = specs.formParam("name", name).formParam("email", email)
                .when().post(PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPECUSTOMERAPIENDPOINT));
        resp = response.asString();

        customer_id = JsonPathImpl.extractValueFromResponse(resp, "id");
        String actualName = JsonPathImpl.extractValueFromResponse(resp, "name");
        String actualEmailID = JsonPathImpl.extractValueFromResponse(resp, "email");

        response.then().assertThat().statusCode(200);
        Assert.assertEquals(actualName, name);
        Assert.assertEquals(actualEmailID, email);
    }

    @Test(testName = "Validate creation of Customer without authentication")
    public void createCustomerWithoutAuthentication() {
        response = given()
                .when().post(PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();
        resp = response.asString();

        response.then().assertThat().statusCode(401);
        String errorMessage = JsonPathImpl.extractValueFromResponse(resp, "error.message");
        String errorType = JsonPathImpl.extractValueFromResponse(resp, "error.type");

        Assert.assertEquals(errorMessage, errorMessageForMissingAPIKey);
        Assert.assertEquals(errorType, errorMessageTypeForMissingAPIKey);
    }

    @Test(testName = "Validate customer list")
    public void getCustomerList() {

        response = specs.queryParam("limit", "3").get(PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPECUSTOMERAPIENDPOINT));
        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        resp = response.asString();
        JsonPath js = JsonPathImpl.rawToJSON(resp);
        int listSize = js.getList("data").size();
        Assert.assertEquals(listSize, 3);

    }
    @Test(dependsOnMethods = {"createCustomersWithFormParameters"})
    @Ignore
    public void getCustomerBasedOnID(){

        response = specs.pathParam("id", customer_id)
                .when().get(PropertiesFileImpl.getDataFromPropertyFile(ConfigProperties.STRIPECUSTOMERAPIENDPOINT) + "/:{id}");
        response.prettyPrint();
        resp = response.asString();


    }

}
