import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreationOrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private User user;
    private OrderCredentials orderCreds;

    @Before
    public void setUp() {
        user = UserGenerator.generic();
        userClient.userRegistration(user);
        orderCreds = new OrderCredentials(orderClient);
    }

    @Test
    public void placeOrderWithoutIngredientsAuthorized() {
        orderCreds.setIngredients(new String[0]);
        orderClient.placeOrder(user, orderCreds, true)
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public  void placeOrderWithWrongIngredientHash() {
        String[] list = {"61c0c5a71d1f82001bdaaagf", "61c0c5a71d1f823001bdaaa6d"};
        orderCreds.setIngredients(list);
        orderClient.placeOrder(user, orderCreds, true)
                .assertThat()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    public void placeCorrectOrderAuthorized() {
        orderClient.placeOrder(user, orderCreds, true)
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void placeCorrectOrderNonAuthorised() {
        orderClient.placeOrder(user, orderCreds, false)
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @After
    public void userCleanUp() {
        userClient.deleteRegisteredUser(user);
    }

}
