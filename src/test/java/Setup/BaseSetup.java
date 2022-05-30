package Setup;

import POJO.LoginPOJO;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class BaseSetup {

    protected static String authToken;
    protected static Integer userId;
    private static Response response;


    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "http://training.skillo-bg.com:3100";
        BaseSetup.getToken();
        getUserId();
    }

    private static void getToken(){
        LoginPOJO login = new LoginPOJO();

        login.setUsernameOrEmail("test91");
        login.setPassword("test91");

        response = given()
                .header("Content-Type", "application/json")
                .body(login)
                .when()
                .post("/users/login");

        String loginResponseBody = response.getBody().asString();
        authToken = JsonPath.parse(loginResponseBody).read("$.token");
        System.out.println("This is the login token: " + authToken);
    }

    private static void getUserId(){

        String loginResponseBody = response.getBody().asString();
        authToken = JsonPath.parse(loginResponseBody).read("$.token");
        System.out.println("Extracted token is: " + authToken);

        userId = JsonPath.parse(loginResponseBody).read("$.user.id");
    }


}