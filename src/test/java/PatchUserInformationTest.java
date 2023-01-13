import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PatchUserInformationTest {
    private User user;
    private ValidatableResponse valResp;
    private final UserClient userClient = new UserClient();

    @Before
    public void setUp() {
        user = UserGenerator.generic();
        valResp = userClient.userRegistration(user);
        user.setAccessToken(valResp.extract().path("accessToken"));
    }

    @Test
    public void patchUserInfoAuthorized() {
        given()
                .header("Authorization", user.getAccessToken())
                .header("Content-type", "application/json")
                .baseUri("https://stellarburgers.nomoreparties.site")
                .and()
                .body("{\"email\": \"new_email@ya.ru\", \"name\": \"daniel\"}")
                .when()
                .patch("/api/auth/user");

        userClient.userGetInfo(user)
                .assertThat()
                .body("user.email", equalTo("new_email@ya.ru"))
                .and()
                .body("user.name", equalTo("daniel"));
    }

    @Test
    public void patchUserInfoNonAuthorized() {
        given()
                .header("Content-type", "application/json")
                .baseUri("https://stellarburgers.nomoreparties.site")
                .and()
                .body("{\"email\": \"new_email@ya.ru\", \"name\": \"daniel\"}")
                .when()
                .patch("/api/auth/user")
                .then().log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @After
    public void userCleanUp() {
        given().log().all()
                .header("Authorization", user.getAccessToken())
                .when()
                .delete("https://stellarburgers.nomoreparties.site/api/auth/user");
    }
}
