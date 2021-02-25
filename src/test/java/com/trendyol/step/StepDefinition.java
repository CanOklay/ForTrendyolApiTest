package com.trendyol.step;

import com.trendyol.Movie;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class StepDefinition {

    private static final String BASE_URL = "http://www.omdbapi.com";
    public static final String API_KEY = "apikey";
    public static final String API_KEY_PARAMETER = "a89c077";
    public static final String SEARCH_KEY = "/?s=";
    public static final String ID_SEARCH_KEY = "/?i";
    private Response response;
    private static String imdbID;

    @Given("Set the API")
    public void getListMovies() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("Search {string}")
    public void searchMovies(String movie) {
        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + SEARCH_KEY + movie + "&" + API_KEY + "=" + API_KEY_PARAMETER);
    }

    @Then("Check the status code {int}")
    public void checkTheStatusCode(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @And("Check the {string} and get id")
    public void checkTitleAndGetId(final String expectedTitle) {
        List<Movie> movies = response.body().jsonPath().getList("Search", Movie.class);

        Optional<Movie> optionalMovie = movies.stream()
                .filter(m -> m.getTitle().equals(expectedTitle))
                .findFirst();
        assertTrue(optionalMovie.isPresent());

        Movie movie = optionalMovie.get();
        assertEquals(expectedTitle, movie.getTitle());
        System.out.println(movie.getYear());
        assertNotNull(movie.getYear());
        imdbID = movie.getImdbID();
        System.out.println(imdbID);
    }

    @When("Search by id")
    public void searchById() {
        response = given()
                .when()
                .get(BASE_URL + ID_SEARCH_KEY + "=" + imdbID + "&" + API_KEY + "=" + API_KEY_PARAMETER);
    }

    @Then("Check the title {string}")
    public void checkTheTitle(String expectedTitle) {
        response.then()
                .body("Title", equalTo(expectedTitle))
                .body("Year", notNullValue())
                .body("Released", notNullValue())
                .log().all();
        System.out.println("asdf");
    }
}
