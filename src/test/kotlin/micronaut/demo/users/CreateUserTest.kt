package micronaut.demo.users

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.client.netty.DefaultHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import javax.inject.Inject

@MicronautTest // Tell JUnit to run the test as a Micronaut Test
class CreateUserTest {

    @Inject
    lateinit var server: EmbeddedServer // Have Micronaut startup and inject an in-memory instance of the application

    private lateinit var client: HttpClient

    @BeforeEach
    fun setup() {
        client =
            DefaultHttpClient(server.url) // Create a client before each test that will be used to call the embedded server
    }

    @AfterEach
    fun teardown() {
        client.close() // Close the client after the test finishes
    }

    @Test
    fun successfully_creating_a_user() {
        // Create the json object as a map
        val user = mutableMapOf(Pair("userName", "kevin"), Pair("password", "pass"))
        // Create a request that will call POST /users endpoint on the server
        val request = HttpRequest.POST("/users", user)

        // Call the server
        val response = client.toBlocking().exchange(request, Map::class.java)!!
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

    private fun validUser(): MutableMap<String, String> {
        return mutableMapOf(Pair("userName", "kevin"), Pair("password", "pass"))
    }

    private fun createUser(user: MutableMap<String, String>): HttpResponse<Map<*, *>> {
        // Create a request that will call POST /users endpoint on the server
        val request = HttpRequest.POST("/users", user)
        // Call the server
        return client.toBlocking().exchange(request, Map::class.java)!!
    }

    private fun assertResponseIsBadRequest(request: MutableMap<String, String>) {
        assertThrows<HttpClientResponseException> {
            try {
                createUser(request) // Since this will return a 400, HttpClient will throw an exception, so to validate the behavior we need to wrap it in a try/catch
            } catch (e: HttpClientResponseException) {
                assertEquals(HttpStatus.BAD_REQUEST, e.status)
                throw e
            }
        }
    }
}
