package br.com.capgemini.rogersilva.restassured.marvel;

import static org.springframework.http.HttpStatus.OK;

import org.hamcrest.CoreMatchers;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.capgemini.rogersilva.restassured.BaseTest;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;

@DisplayName("Characters endpoints")
public class CharactersTests extends BaseTest {

    private static final String END_POINT = "/characters";

    private static final String SCHEMA_CHARACTERS = "schemas/characters-schema.json";

    private static final String CODE = "code";

    private static final String STATUS = "status";

    @Test
    @DisplayName("Fetches lists of character")
    public void testCharacters() {
        RestAssured.with().spec(requestSpec).when().get(endPointWithSuffix(END_POINT)).then().spec(responseSpec)
                .statusCode(OK.value()).body(CODE, CoreMatchers.equalTo(OK.value()))
                .body(STATUS, IsEqualIgnoringCase.equalToIgnoringCase(OK.getReasonPhrase())).assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(SCHEMA_CHARACTERS));
    }
}