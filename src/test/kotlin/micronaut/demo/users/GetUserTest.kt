package micronaut.demo.users

import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetUserTest : BaseUsersControllerTest() {
    @Test
    fun successfully_retrieve_a_user_that_has_been_created() {
        val user = validUser()

        // Create user and assert that it was successful
        val createResponse = createUser(user)
        assertEquals(HttpStatus.CREATED, createResponse.status)

        // Get user from server
        val getResponse = getUser(user["userName"]!!)
        val body = getResponse.body()!!

        // Assert response is OK
        assertEquals(HttpStatus.OK, getResponse.status)
        // Assert userName is correct
        assertEquals(user["userName"], body["userName"])
        // Assert password is not returned
        assertFalse(body.containsKey("password"))
    }

    @Test
    fun when_requesting_an_account_that_does_not_exist_return_404() {
        assertThrows<HttpClientResponseException> {
            try {
                // Attempt to get a user that does not exist
                getUser("non-existing-user")
            } catch (e: HttpClientResponseException) {
                // It should return 404: Not found
                assertEquals(HttpStatus.NOT_FOUND, e.status)
                throw e
            }
        }
    }

    @Test
    fun should_be_case_insensitive() {
        val user = validUser()

        // Create user and assert that it was successful
        val createResponse = createUser(user)
        assertEquals(HttpStatus.CREATED, createResponse.status)

        val userName = user["userName"].toString()
        // Assert that the response of GET /users/kevin = GET /users/KEVIN
        assertEquals(getUser(userName.toLowerCase()).body(), getUser(userName.toUpperCase()).body())
    }
}
