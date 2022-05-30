package Tests.UserTests.PositiveUserAccountTests;

import Setup.BaseSetup;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetUserInfo extends BaseSetup {

    public void getUserData() {

        Response userProfile = given()
                .pathParam("userId", userId )
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .when()
                .get("/{userId}");

        userProfile
                .then()
                .body("id", equalTo(userId))
                .statusCode(200);
    }
}