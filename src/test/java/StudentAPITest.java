import annotations.CustomFrameworkAnnotations;
import base.BaseTest;
import dto.StudentData;
import enums.StudentSpecs;
import enums.TestCaseType;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.StudentPojo;
import utils.JsonPathImpl;
import utils.PropertiesFileImpl;

import java.util.List;

import static io.restassured.RestAssured.given;

public class StudentAPITest extends BaseTest {

    /*
    To pull the student app image, run docker pull tejasn1/student-app
    You can launch the student-app using the command below
    docker run -p 8085:8080 -d tejasn1/student-app
    You can then access the student app on the url http://localhost:8085/student/list
     */
    Response response;
    JsonPath js;
    String resp;
    String studentEndpoint;
    int studID;
    List<Integer> studentID;
    List<String> studentNames;
    StudentData studentData;
    StudentPojo stdData;

    @BeforeClass(alwaysRun = true)
    public void init() {
        studentData = new StudentData();
        RestAssured.baseURI = PropertiesFileImpl.getDataFromPropertyFile(StudentSpecs.STUDENTBASEURI);
        RestAssured.port = Integer.parseInt(PropertiesFileImpl.getDataFromPropertyFile(StudentSpecs.STUDENTPORT));
        studentEndpoint = PropertiesFileImpl.getDataFromPropertyFile(StudentSpecs.STUDENTAPIENDPOINT);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate fetching of all the Students", groups = {"regression"})
    public void getAllStudent() {

        response = given().when().get(studentEndpoint + "/list");

        response.prettyPrint();
        //response.then().assertThat().statusCode(200);
        Assert.assertEquals(response.getStatusCode(), 200); //TestNG assertion by validating on the response
        resp = response.asString();
        js = JsonPathImpl.rawToJSON(response.asString());

        studentID = js.getList("id");
        studID = studentID.get(0);
        studentNames = js.getList("firstName");
        studentNames.forEach(System.out::println);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate fetching of Student based on specified query parameters",  groups = {"regression"})
    public void getFilteredStudents() {

        int limit = 1;
        response = given()
                .queryParam("programme", "Computer Science")
                .queryParam("limit", limit)
                .when().get(studentEndpoint + "/list")
                .then().extract().response();

        response.prettyPrint();
        js = JsonPathImpl.rawToJSON(response.asString());

        int list_size = js.getList("$").size();
        Assert.assertEquals(list_size, limit);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate fetching of Student based on the path parameters", dependsOnMethods = {"getAllStudent"},  groups = {"regression"})
    public void getSpecifiedStudent() {

        response = given()
                .pathParam("id", studID)
                .when().get(studentEndpoint + "/{id}")
                .then().extract().response();

        response.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.E2E)
    @Test(testName = "Validate adding of new Student",  groups = {"regression"})
    public void createAStudent() {

        stdData = studentData.createStudentData();
        String payload = "{\"firstName\":\"" + stdData.getFirstName() + "\"," +
                "\"lastName\":\"" + stdData.getLastName() + "\"," +
                "\"email\":\"" + stdData.getEmail() + "\"," +
                "\"programme\":\"" + stdData.getProgramme() + "\"," +
                "\"courses\":[\"C++\",\"JAVA\"]}"; //TODO - Passing stdData course Arraylist is giving issues

        response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when().post(studentEndpoint)
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student added");
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.E2E)
    @Test(testName = "Validate adding of new Student using Pojo payload",  groups = {"regression"})
    public void createAStudentWithPojoPayload() {

        stdData = studentData.createStudentData();
        response = given()
                .header("Content-Type", "application/json")
                .body(stdData)
                .when().post(studentEndpoint)
                .then().extract().response();

        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student added");
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Update details for the specified student",  groups = {"regression"})
    public void updateSpecifiedStudent() {

        stdData = studentData.updateStudentData();
        response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", 104)
                .body(stdData)
                .when().put(studentEndpoint + "/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Student Updated");

    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.INTEGRATION)
    @Test(testName = "Validate updation of Email ID for the specified student",  groups = {"regression"})
    public void updateStudentEmailID() {

        stdData = studentData.updateEmailAddress();
        response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", 104)
                .body(stdData)
                .when().patch(studentEndpoint + "/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(200);

        String resp = response.asString();
        String actualMsg = JsonPathImpl.extractValueFromResponse(resp, "msg");
        Assert.assertEquals(actualMsg, "Updated");
    }

    @CustomFrameworkAnnotations(testCaseType = TestCaseType.E2E)
    @Test(testName = "Validate deletion of the Specified Student",  groups = {"regression"})
    public void deleteSpecifiedStudent() {

        stdData = studentData.createStudentData();
        given()
                .header("Content-Type", "application/json")
                .body(stdData)
                .when().post(studentEndpoint);

        response = given().when().get(studentEndpoint + "/list");
        js = JsonPathImpl.rawToJSON(response.asString());
        studentID = js.getList("id");
        studID = studentID.get(studentID.size() - 1);
        System.out.println(studID);

        response = given()
                .pathParam("id", studID)
                .when().delete(studentEndpoint + "/{id}");

        response.prettyPrint();
        response.then().assertThat().statusCode(204);
    }
}
