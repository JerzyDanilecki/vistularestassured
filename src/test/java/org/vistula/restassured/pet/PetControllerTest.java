package org.vistula.restassured.pet;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.vistula.restassured.RestAssuredTest;

import javax.swing.text.AbstractDocument;
import java.lang.reflect.Type;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;

public class PetControllerTest extends RestAssuredTest {

    @Test
    public void shouldGetAll() {
        given().get("/pet")
                .then()
                .log().all()
                .statusCode(200);
               // .body("size()", is(4));
    }

    @Test
    public void shouldGetFirstPet() {
        given().get("/pet/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", equalTo("Cow"));
    }

    @Test
    public void shouldCreateNewPet() {
        JSONObject requestParams = new JSONObject();
        int value = 4;
        requestParams.put("id", value);
        requestParams.put("name", "Wolf");

        given().header
                ("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);

      /*  given().delete("pet/"+value)
                .then()
                .log().all()
                .statusCode(204);

       */
    }
    @Test
    public void shouldCreatePet2() {
        JSONObject requestParams = new JSONObject();
        int value = 2;
        requestParams.put("id", value);
        requestParams.put("name", "Dog");

        given().header
                ("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);
    }

            @Test
    public void shouldGetErrorPet() {
        int statusCode = given().get("/pet/100")
                .then()
                .log().all()
                .statusCode(404)
                .body(equalTo("There is not Pet with such id"))
                .extract().statusCode();

        assertThat(statusCode).isEqualTo(404);
    }

    @Test
    public void shouldGetSecondPet() {
        given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", is(2))
                .body("name", equalTo("Dog"));
    }

    @Test
    public void shouldGetSsecondPet() {
        Pet pet = given().get("/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(Pet.class);
        assertThat(pet.getId()).isEqualTo(2);
        assertThat(pet.getName()).isEqualTo("Dog");
    }
    @Test
    public void shouldRemovePet() {

        given().delete("/pet/988547452")
                .then()
                .log().all()
                .statusCode(204);
    }
    @Test
    public void shouldCreateNewPet2() {
        JSONObject requestParams = new JSONObject();
        int value = ThreadLocalRandom.current().nextInt(20,Integer.MAX_VALUE);
        requestParams.put("id", value);
        requestParams.put("name", RandomStringUtils.randomAlphabetic(10));

        given().header
                ("Content-Type", "application/json")
                .body(requestParams.toString())
                .post("/pet")
                .then()
                .log().all()
                .statusCode(201);

        given().delete("pet/"+value)
                .then()
                .log().all()
                .statusCode(204);
    }
    @Test
    public void updateUsingPatch () {

        JSONObject requestParams = new JSONObject();
        // int value = ThreadLocalRandom.current().nextInt(20,Integer.MAX_VALUE);
        requestParams.put("id", 2);
        requestParams.put("name",9);

        given().header
                ("Content-Type", "application/json")
                .body(requestParams.toString())
                .log().all()
                .patch("/pet/2",requestParams)
                .then()
                .statusCode(201);


    }

}



