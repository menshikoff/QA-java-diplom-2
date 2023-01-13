import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUseTest {
    private User user;
    private final UserClient userClient = new UserClient();

    @Before
    public void setUp() {
        user = UserGenerator.generic();
        userClient.userRegistration(user);
    }

    @Test
    public void logInWithCorrectCreds() {
        ValidatableResponse valResp = userClient.userLogin(user);

        valResp
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void logInWithIncorrectLogin() {

        user.setEmail("qweerty@ere.re");

        userClient.userLogin(user)
                .assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void logInWithIncorrectPassword() {

        user.setPassword("123456q");

        userClient.userLogin(user)
                .assertThat()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @After
    public void userCleanUp() {
        userClient.deleteRegisteredUser(user);
    }

}
