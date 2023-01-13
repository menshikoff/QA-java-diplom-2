import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class CreationUserWithoutRequiredFieldTest {
    private User user;
    private int expectedError;
    private String expectedMessage;

    public CreationUserWithoutRequiredFieldTest(User user, int expectedError, String expectedMessage) {
        this.user = user;
        this.expectedError = expectedError;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object [][] getDate() {
        return new Object[][] {
                {UserGenerator.genericNoName(), HttpStatus.SC_FORBIDDEN, "Email, password and name are required fields"},
                {UserGenerator.genericNoPassword(), HttpStatus.SC_FORBIDDEN, "Email, password and name are required fields"},
                {UserGenerator.genericNoEmail(), HttpStatus.SC_FORBIDDEN, "Email, password and name are required fields"},
        };
    }

    @Test
    public void shouldReturnMistakeAsRequiredFieldIsMissing() {
        UserClient userClient = new UserClient();
        ValidatableResponse valResp = userClient.userRegistration(user);
        valResp
                .assertThat()
                .body("message", equalTo(expectedMessage))
                .and()
                .statusCode(expectedError);
    }

}
