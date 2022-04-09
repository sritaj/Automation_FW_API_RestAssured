import base.BaseTest;
import dto.PayPalProductData;
import enums.PayPalSpecs;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.PayPalProductPojo;
import utilities.JsonPathImpl;
import utilities.PropertiesFileImpl;

import static io.restassured.RestAssured.given;

public class PayPalOAuthTest extends BaseTest {

    RequestSpecification specsForAuth;
    RequestSpecification specs;
    Response response;
    String resp;
    String accessToken;
    final String username = PropertiesFileImpl.getDataFromPropertyFile(PayPalSpecs.PAYPALCLIENTACCESSID);
    final String password = PropertiesFileImpl.getDataFromPropertyFile(PayPalSpecs.PAYPALCLIENTSECRETID);
    PayPalProductPojo ppojo;
    PayPalProductData pdata;
    final String paypalIDKey = "PayPal-Request-Id";
    final String paypalIDValue = "PRODUCT-18062020-001";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = PropertiesFileImpl.getDataFromPropertyFile(PayPalSpecs.PAYPALOAUTHURI);
        specsForAuth = given().param("grant_type", "client_credentials").auth().preemptive().basic(username, password);
        pdata = new PayPalProductData();
    }

    @Test(testName = "Validate retrieval of Access Token")
    public void getAccessToken() {
        response = specsForAuth.post("/v1/oauth2/token").then().extract().response();
        response.prettyPrint();
        resp = response.asString();
        accessToken = JsonPathImpl.extractValueFromResponse(resp, "access_token");

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(testName = "Validate creation of product", dependsOnMethods = "getAccessToken")
    public void createProduct() {
        ppojo = pdata.createPayPalProduct();
        specs = given().auth().oauth2(accessToken).contentType(ContentType.JSON);
        response = specs.header(paypalIDKey, paypalIDValue)
                .body(ppojo)
                .when()
                .post("/v1/catalogs/products")
                .then().extract().response();

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }


}
