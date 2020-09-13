package micronaut.demo.users

import io.micronaut.http.HttpStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

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
}
