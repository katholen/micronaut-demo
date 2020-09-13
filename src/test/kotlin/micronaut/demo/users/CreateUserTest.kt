package micronaut.demo.users

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateUserTest : BaseUsersControllerTest() {

    @Test
    fun successfully_creating_a_user() {
        // Create the json object as a map
        val user = validUser()

        // Call the server
        val response = createUser(user)
        val body = response.body()!!

        // Status should be 201
        assertEquals(HttpStatus.CREATED, response.status)
        // Username should be the same as the userName supplied
        assertEquals(user["userName"], body["userName"])
        // The password should not be returned in a response
        assertFalse(body.containsKey("password"))
    }

    @Test
    fun submitting_a_request_with_an_empty_userName_should_return_a_400() {
        // Create a valid request and set the userName equal to an empty string
        val request = validUser()
        request["userName"] = ""

        // Call server and the response should be a 400: Bad Request
        assertResponseIsBadRequest(request)
    }

    @Test
    fun submitting_a_request_with_an_empty_password_should_return_a_400() {
        // Create a valid request and set the userName equal to an empty string
        val request = validUser()
        request["password"] = ""

        // Call server and the response should be a 400: Bad Request
        assertResponseIsBadRequest(request)
    }

    private fun assertResponseIsBadRequest(request: MutableMap<String, String>) {
        assertThrows<HttpClientResponseException> {
            try {
                createUser(request) // Since this will return a 400 HttpClient will throw an exception, so to validate the behavior we wrap it in a try/catch
            } catch (e: HttpClientResponseException) {
                assertEquals(HttpStatus.BAD_REQUEST, e.status)
                throw e
            }
        }
    }

    @Test
    fun unable_to_create_a_user_with_an_existing_userName() {
        // Create a valid request and set the userName equal to an empty string
        val request = validUser()

        // Create the user successfully and assert that it was successful
        val response = createUser(request)
        assertEquals(HttpStatus.CREATED, response.status)

        // Attempt to create a user with the same userName and it should fail
        assertThrows<HttpClientResponseException> {
            try {
                createUser(request)
            } catch (e: HttpClientResponseException) {
                assertEquals(HttpStatus.CONFLICT, e.status)
                throw e
            }
        }
    }
}
