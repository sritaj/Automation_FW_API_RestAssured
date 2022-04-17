package examples;

import com.github.javafaker.Faker;
import constants.FrameworkConstants;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.JsonPathImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class DynamicJsonTest {

    Faker faker = new Faker();
    String aisle = faker.address().streetAddressNumber();
    String isbn = faker.address().countryCode();
    String bookName = faker.book().title();
    String authorName = faker.book().author();
    RequestSpecification spec;
    String path = FrameworkConstants.getJsonFilePath() + "/librarybook.json";

    String book = "{\n" +
            "\n" +
            "\"name\":\"" + bookName + "\",\n" +
            "\"isbn\":\"" + isbn + "\",\n" +
            "\"aisle\":\"" + aisle + "\",\n" +
            "\"author\":\"" + authorName + "\"\n" +
            "}\n";


    public static String getBookData(String bookName, String isbn, String aisle, String authorName) {

        return "{\n" +
                "\n" +
                "\"name\":\"" + bookName + "\",\n" +
                "\"isbn\":\"" + isbn + "\",\n" +
                "\"aisle\":\"" + aisle + "\",\n" +
                "\"author\":\"" + authorName + "\"\n" +
                "}\n";
    }

    @DataProvider(name = "booksData")
    public Object[][] getData() {
        return new Object[][]{
                {faker.address().streetAddressNumber(), faker.address().countryCode(), faker.book().title(), faker.book().author()},
                {faker.address().streetAddressNumber(), faker.address().countryCode(), faker.book().title(), faker.book().author()},
                {faker.address().streetAddressNumber(), faker.address().countryCode(), faker.book().title(), faker.book().author()}};
    }

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = "http://216.10.245.166";
        RestAssured.basePath = "Library/Addbook.php";
        spec = given().log().all().header("Content-Type", "application/json");
    }

    @Test(testName = "Validate Adding of New Book")
    public void addBookUseCase1() {
        String response = spec
                .body(book).when().post()
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        String bookid = JsonPathImpl.extractValueFromResponse(response, "ID");
        System.out.println(bookid);

    }

    @Test(testName = "Validate Adding of New Book using TestNG Data Provider", dataProvider = "booksData")
    public void addBookUseCase2(String bookName, String isbn, String aisle, String authorName) {
        String response = spec
                .body(getBookData(bookName, isbn, aisle, authorName)).when().post()
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        String bookid = JsonPathImpl.extractValueFromResponse(response, "ID");
        System.out.println(bookid);

    }

    @Test(testName = "Validate Adding of New Book via reading from JSON file")
    @Ignore
    public void addBookUseCase3() throws IOException {
        spec
                .body(new String(Files.readAllBytes(Paths.get(path))))
                .when().post()
                .then().assertThat().statusCode(200);

    }
}
