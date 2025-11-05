package ru.yandex.practicum.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.steps.UserSteps;

import static org.hamcrest.CoreMatchers.*;

public class LoginUserTests extends BaseTest {

    private UserSteps userSteps = new UserSteps();
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@mail.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(12));
        user.setName(RandomStringUtils.randomAlphabetic(12));
        accessToken = userSteps.createUser(user)
                 .extract()
                 .path("accessToken");

    }

    @Test
    //пользователь может авторизоваться
    public void shouldLoginUserTest() {
        userSteps
                .loginUser(user)
                .statusCode(200)
                .body("success", is(true));


    }

    //ошибка при вводе несуществующего пароля
    @Test
    public void shouldNotLoginWithWrongPasswordTest() {
        user.setPassword("whdehgcgshe@mail.ru");
        userSteps.loginUser(user)
                .statusCode(401)
                .body("message", containsString("email or password are incorrect"));
    }

    //ошибка при вводе несуществующего емейла
    @Test
    public void shouldNotLoginWithWrongEmailTest() {
        user.setEmail("fkjhrigfrgdu@mail.ru");
        userSteps.loginUser(user)
                .statusCode(401)
                .body("message", containsString("email or password are incorrect"));
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken)
                    .statusCode(202);
        }
    }

}
