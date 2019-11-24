package org.vistula.restassured.information;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;
import org.vistula.restassured.pet.Information;

import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class InformationControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/information")
                .then()
                .log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    public void shouldAddBob() {
        JSONObject requestParams = new JSONObject();
        String player = RandomStringUtils.randomAlphabetic(10);
        requestParams.put("name", player);
        String poland = "Poland";
        requestParams.put("nationality", poland);
        int value1 = 1000000;
        requestParams.put("salary", value1);

        Information info = given().header
                ("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/information")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(Information.class);

        assertThat(info.getId()).isGreaterThan(0);
        assertThat(info.getName()).isEqualTo(player);
        assertThat(info.getNationality()).isEqualTo(poland);
        assertThat(info.getSalary()).isEqualTo(value1);
    }
    @Test
    public void shouldRemoveId() {

        given().delete("/information/1")
                .then()
                .log().all()
                .statusCode(204);
    }
}
