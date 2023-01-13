import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;

public class CreationUserTest {
    private User user;
    private final UserClient userClient = new UserClient();

    @Before
    public void setUp() {
        user = UserGenerator.generic();
    }

    @Test
    public void creationUniqueUser() {
        userClient.userRegistration(user)
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(HttpStatus.SC_OK);

        userClient.deleteRegisteredUser(user);
    }

    @Test
    public void creationOfExistingUser() {
        userClient.userRegistration(user);

        userClient.userRegistration(user)
                .assertThat()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(HttpStatus.SC_FORBIDDEN);

        userClient.deleteRegisteredUser(user);
    }

    @Test
    public void creationOfUserWithoutEmail() {
        user.setEmail("");
        userClient.userRegistration(user)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void creationOfUserWithoutPassword() {
        user.setPassword("");
        userClient.userRegistration(user)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
