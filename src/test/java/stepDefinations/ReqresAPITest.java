package stepDefinations;

import dto.UserData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojo.UserPojo;
import utilities.JsonPathImpl;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class ReqresAPITest {

    UserData udata;
    UserPojo upojo;
    Response response;
    String resp;
    RequestSpecification spec;
    String userID;

    @Given("As an user")
    public void as_an_user() {
        udata = new UserData();
        upojo = udata.createUser();
        spec = given().contentType(ContentType.JSON).body(upojo);

    }

    @When("I add a resource by providing name and job")
    public void i_add_a_resource_by_providing_and() {
        response = spec
                .when().post("/users");
    }

    @Then("I should get {string} response with id")
    public void i_should_get_response_with_id(String string) {
        resp = response.asString();
        userID = JsonPathImpl.extractValueFromResponse(resp, "id");
        Assert.assertNotNull(userID);
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(string));
        response.prettyPrint();
        System.out.println(userID);
    }

    @Given("A list of users are available")
    public void a_list_of_users_are_available() {
        spec = given();
    }

    @Given("I specify the pagecount as {string}")
    public void i_specify_the_pagecount_as(String string) {
        spec.queryParam("page", string);
    }

    @When("I fetch the users")
    public void i_fetch_the_users() {
        response = spec.when().get("/users");
    }

    @Then("I should get {string} response and page value as {string} in response body")
    public void i_should_get_response_and_page_value_as_in_response_body(String string, String string2) {
        response.prettyPrint();
        resp = response.asString();
        String pageCount = JsonPathImpl.extractValueFromResponse(resp, "page");

        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(string));
        Assert.assertEquals(pageCount, string2);

    }

    @When("I fetch the user by passing id {string}")
    public void i_fetch_the_user_by_passing_id(String string) {

        response = when().get("/users/" + string);
    }

    @Then("I should get {string} response and the response body with details")
    public void i_should_get_response_and_the_response_body_with_details(String string, io.cucumber.datatable.DataTable dataTable) {

        List<Map<String,String>> data = dataTable.asMaps(String.class,String.class);
        resp = response.asString();
        String email = JsonPathImpl.extractValueFromResponse(resp, "data.email");
        String firstName = JsonPathImpl.extractValueFromResponse(resp, "data.first_name");
        String lastName = JsonPathImpl.extractValueFromResponse(resp, "data.last_name");

        Assert.assertEquals(email, data.get(0).get("email"));
        Assert.assertEquals(firstName, data.get(0).get("first_name"));
        Assert.assertEquals(lastName, data.get(0).get("last_name"));


    }
}
