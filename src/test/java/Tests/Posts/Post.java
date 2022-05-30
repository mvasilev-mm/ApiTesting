package Tests.Posts;

import POJO.PostPOJO;
import Setup.BaseSetup;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

public class Post extends BaseSetup {
    public void makeAPost() {
        PostPOJO post = new PostPOJO("My new post", "\"https://i.imgur.com/HUdmONP.jpg", "public");
        // String convertedPost = objectMapper.writeValueAsString(post);


        Response postsResponse = given()

                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(post)
                .when()
                .post("/posts" );

        postsResponse
                .then()
                .statusCode(201);
    }

    public void get1Post() {
        ///users/{userId}/posts
        Response postsResponse = given()
                .queryParam("postStatus", "public")
                .queryParam("take", 5)
                .queryParam("skip", 0)
                .header("Content-Type", "application/json")
                .pathParam("userId", userId)
                .header("Authorization", "Bearer " + authToken)
                .when()
                .get( "/{userId}" + "/posts" );

        postsResponse
                .then()
                .body("", Matchers.hasSize(1))
                .statusCode(200);
    }
}