import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class UserClient {

    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    public ValidatableResponse userRegistration(User user) {
        ValidatableResponse valResp =
                given().log().all()
                        .header("Content-type", "application/json")
                        .baseUri(BASE_URI)
                        .and()
                        .body(user)
                        .when()
                        .post("/api/auth/register")
                        .then().log().all();

        if (valResp.extract().path("accessToken") != null) {
            user.setAccessToken(valResp.extract().path("accessToken"));
        }
        return valResp;
    }

    public void deleteRegisteredUser(User user) {
        given().log().all()
                .header("Authorization", user.getAccessToken())
                .when()
                .delete("https://stellarburgers.nomoreparties.site/api/auth/user");
    }
    public ValidatableResponse userLogin(User user) {
        UserCredentials creds = UserCredentials.from(user);
        ValidatableResponse valResp =
                given().log().all()
                .header("Content-type", "application/json")
                .baseUri(BASE_URI)
                .and()
                .body(creds)
                .when()
                .post("/api/auth/login")
                .then().log().all();

        if (valResp.extract().path("accessToken") != null) {
            user.setAccessToken(valResp.extract().path("accessToken"));
        }
                return valResp;
    }

    public ValidatableResponse userGetInfo(User user) {
        return given().log().all()
                .header("Authorization", user.getAccessToken())
                .baseUri(BASE_URI)
                .when()
                .get("/api/auth/user")
                .then().log().all();
    }

    public ValidatableResponse getUserListOfOrdersAuthorised(User user) {
        return given().log().all()
                .header("Authorization", user.getAccessToken())
                .header("Content-type", "application/json")
                .baseUri(BASE_URI)
                .when()
                .get("/api/orders")
                .then().log().all();
    }

    public ValidatableResponse getUserListOfOrdersNonAuthorised() {
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(BASE_URI)
                .when()
                .get("/api/orders")
                .then().log().all();
    }
}
