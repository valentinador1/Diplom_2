package ru.yandex.practicum.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.steps.UserSteps;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class CreateUserTests extends BaseTest {

    private UserSteps userSteps = new UserSteps();
    private User user;
    private String accessToken;
    private String accessToken2;

    @Before
    public void setUp() {
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@mail.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(12));
        user.setName(RandomStringUtils.randomAlphabetic(12));

    }

    @Test
    //пользователя можно создать
    public void shouldCreateUserTest() {
        accessToken = userSteps
                .createUser(user)
                .statusCode(200)
                .body("success", is(true))
                .extract()
                .path("accessToken");

    }

    @Test
    //нельзя создать двух одинаковых пользователей
    public void shouldNotCreateTwoSameUsersTest() {
        accessToken = userSteps.createUser(user)
                .statusCode(200)
                .body("success", is(true))
                .extract()
                .path("accessToken");


        accessToken2 = userSteps.createUser(user) //accessToken на случай непредвиденного создания пользователя
                .statusCode(403)
                .body("message", containsString("User already exists"))
                .extract()
                .path("accessToken");
    }


    //нельзя создать пользователя без емейла
    @Test
    public void shouldNotCreateWithoutEmailTest() {
        user.setEmail(null);
        accessToken = userSteps.createUser(user) //accessToken на случай непредвиденного создания пользователя
                .statusCode(403)
                .body("message", containsString("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    //нельзя создать пользователя  без пароля
    @Test
    public void shouldNotCreateWithoutPasswordTest() {
        user.setPassword(null);
        accessToken = userSteps.createUser(user) //accessToken на случай непредвиденного создания пользователя
                .statusCode(403)
                .body("message", containsString("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    //нельзя создать пользователя  без имени
    @Test
    public void shouldNotCreateWithoutUserNameTest() {
        user.setName(null);
        accessToken = userSteps.createUser(user) //accessToken на случай непредвиденного создания пользователя
                .statusCode(403)
                .body("message", containsString("Email, password and name are required fields"))
                .extract()
                .path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken)
                    .statusCode(202);
        }

        if (accessToken2 != null) {
            userSteps.deleteUser(accessToken2)
                    .statusCode(202);
        }
    }
}
