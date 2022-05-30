package Tests.UserTests.PositiveUserAccountTests;

import POJO.LoginPOJO;
import POJO.RegisterPOJO;
import Setup.BaseSetup;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.*;

public class UserAccountTests extends BaseSetup {

    static String loginToken;
    private static Integer userId;
    private String email;
    private String password;

    @BeforeTest
    public void registerNewUser() throws JsonProcessingException {
        Faker faker = new Faker();
        RegisterPOJO register = new RegisterPOJO();
        LocalDate localDate = LocalDate.now();
        var currentTime = System.currentTimeMillis();
        register.setUsername(faker.name().username());
        register.setEmail("E" + currentTime + "@b.com");
        email = register.getEmail();
        register.setBirthDate(String.valueOf(faker.date().birthday()));
        register.setPassword(localDate + "Aa");
        password = register.getPassword();
        register.setPublicInfo("Hello");


        Response response = given()
                .header("Content-Type", "application/json")
                .body(register)
                .when()
                .post("/users");
        response
                .then()
                .statusCode(201);

        String registerResponseBody = response.getBody().asString();
        System.out.println(registerResponseBody);
        userId = JsonPath.parse(registerResponseBody).read("$.id");
    }

    @Test
    public void loginTest() throws JsonProcessingException {
        LoginPOJO login = new LoginPOJO();

        // set the login credentials to our login object
        login.setUsernameOrEmail(email);
        login.setPassword(password);

        // Convert pojo object to json using Jackson
        /** ObjectMapper objectMapper = new ObjectMapper();
         String convertedJson = objectMapper.writeValueAsString(login);
         System.out.println("CONVERTED JSON IS: ");
         System.out.println(convertedJson); **/

        Response response = given()
                .header("Content-Type", "application/json")
                .body(login)
                .when()
                .post("/users/login");
        response
                .then()
                .statusCode(201);

        // convert the response body json into a string
        String loginResponseBody = response.getBody().asString();

        loginToken = JsonPath.parse(loginResponseBody).read("$.token");

    }

}