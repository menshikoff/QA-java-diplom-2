import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetAllOrdersTest {

    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    ArrayList<Integer> expectedListOfOrders = new ArrayList<>();

    private User user;
    private OrderCredentials order1;
    private OrderCredentials order2;
    private OrderCredentials order3;

    @Before
    public void setUp() {
        user = UserGenerator.generic();
        userClient.userRegistration(user);

        order1 = new OrderCredentials(orderClient);
        order2 = new OrderCredentials(orderClient);
        order3 = new OrderCredentials(orderClient);

        orderClient.placeOrder(user, order1, true);
        orderClient.placeOrder(user, order2, true);
        orderClient.placeOrder(user, order3, true);

        expectedListOfOrders.add(order1.getOrderId());
        expectedListOfOrders.add(order2.getOrderId());
        expectedListOfOrders.add(order3.getOrderId());
    }

    @Test
    public void checkNumberOfOrdersPlacedAreInTheList() {

        ArrayList<Integer> actualListOfOrders =
        userClient.getUserListOfOrdersAuthorised(user)
                        .extract()
                        .body()
                        .path("orders.number");

        Assert.assertEquals(expectedListOfOrders, actualListOfOrders);
    }

    @Test
    public void getAllOrdersOfUserNonAuthorised() {

        userClient.getUserListOfOrdersNonAuthorised()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void clearUp() {
        userClient.deleteRegisteredUser(user);
    }

}
