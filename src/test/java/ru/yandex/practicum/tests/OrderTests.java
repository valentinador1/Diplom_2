package ru.yandex.practicum.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.model.User;

import ru.yandex.practicum.steps.UserSteps;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class OrderTests extends BaseTest {


    private UserSteps userSteps = new UserSteps();
    private User user;
    private String accessToken;
    public static final String ORDER = "/api/orders";

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
    public void shouldCreateOrderWithAuth() {
        Order order = new Order();
        order.setIngredients(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));

        given()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    public void shouldCreateOrderWithoutAuth() {

        Order order = new Order();
        order.setIngredients(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));

        given()
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    public void shouldNotCreateOrderWithoutIngredientsWithAuth() {
        Order order = new Order();
        order.setIngredients(Arrays.asList());

        given()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .statusCode(400)
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    public void shouldNotCreateOrderWithoutIngredientsWithoutAuth() {

        Order order = new Order();
        order.setIngredients(Arrays.asList());

        given()
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .statusCode(400)
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    public void shouldNotCreateOrderWithInvalidIngredientsWithoutAuth() {

        Order order = new Order();
        order.setIngredients(Arrays.asList("jdchuschu", "61c0c5a71d1f82001bdaaa6f"));

        given()
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldNotCreateOrderWithInvalidIngredientsWithAuth() {
        Order order = new Order();
        order.setIngredients(Arrays.asList("jdchuschu", "61c0c5a71d1f82001bdaaa6f"));

        given()
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER)
                .then()
                .statusCode(500);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken)
                    .statusCode(202);
        }
    }
}
