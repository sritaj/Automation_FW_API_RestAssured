import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import reusableMethods.JsonPathImpl;

import static io.restassured.RestAssured.given;

public class DynamicJsonTest {

    Faker faker = new Faker();
    String aisle = faker.address().streetAddressNumber();
    String isbn = faker.address().countryCode();
    String bookName = faker.book().title();
    String authorName = faker.book().author();

    String book = "{\n" +
            "\n" +
            "\"name\":\""+bookName+"\",\n" +
            "\"isbn\":\""+isbn+"\",\n" +
            "\"aisle\":\""+aisle+"\",\n" +
            "\"author\":\""+authorName+"\"\n" +
            "}\n";


    public static String getBookData(String bookName, String isbn, String aisle, String authorName){

        return "{\n" +
                "\n" +
                "\"name\":\""+bookName+"\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\""+authorName+"\"\n" +
                "}\n";
    }

    @DataProvider(name = "booksData")
    public Object[][] getData(){
        return new Object[][]{
                {faker.address().streetAddressNumber(), faker.address().countryCode(), faker.book().title(), faker.book().author()},
                {faker.address().streetAddressNumber(), faker.address().countryCode(), faker.book().title(), faker.book().author()},
                {faker.address().streetAddressNumber(), faker.address().countryCode(), faker.book().title(), faker.book().author()}};
    }

    @Test(testName = "Validate Adding of New Book")
    @Ignore
    public void addBookUseCase1(){
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(book).when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        String bookid = JsonPathImpl.extractValueFromResponse(response, "ID");
        System.out.println(bookid);

    }

    @Test(testName = "Validate Adding of New Book using TestNG Data Provider", dataProvider = "booksData")
    public void addBookUseCase2(String bookName, String isbn, String aisle, String authorName){
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(getBookData(bookName, isbn, aisle, authorName)).when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        String bookid = JsonPathImpl.extractValueFromResponse(response, "ID");
        System.out.println(bookid);

    }
}
