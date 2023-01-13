import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient {

    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    public ValidatableResponse placeOrder(User user, OrderCredentials order, boolean auth) {

        ValidatableResponse valResp;

        if (auth) {
            valResp =
                    given().log().all()
                    .header("Authorization", user.getAccessToken())
                    .header("Content-type", "application/json")
                    .baseUri(BASE_URI)
                    .body(order)
                    .when()
                    .post("/api/orders")
                    .then().log().all();
        }
        else {
            valResp =
                    given().log().all()
                    .header("Content-type", "application/json")
                    .baseUri(BASE_URI)
                    .body(order)
                    .when()
                    .post("/api/orders")
                    .then().log().all();
        }

        try {
            if (valResp.extract().path("order.number") != null) {
                order.setOrderId(valResp.extract().path("order.number"));
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        };

        return valResp;
    }

    public List<String> getCorrectHashCodesOfIngredientsFromApi() {
        return given().log().all()
                .header("Content-type", "application/json")
                .baseUri(BASE_URI)
                .when()
                .get("/api/ingredients")
                .then().log().all()
                .extract()
                .body()
                .path("data._id");
    }
}
