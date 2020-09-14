package micronaut.demo.users

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.netty.DefaultHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import javax.inject.Inject

@MicronautTest
open class BaseUsersControllerTest {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    lateinit var usersMapRepository: UsersMapRepository

    private lateinit var client: HttpClient

    @BeforeEach
    fun setup() {
        client = DefaultHttpClient(server.url)
    }

    @AfterEach
    fun teardown() {
        client.close()
        usersMapRepository.deleteAll()
    }

    protected fun validUser(): MutableMap<String, String> {
        return mutableMapOf(Pair("userName", "kevin"), Pair("password", "pass"))
    }

    protected fun createUser(user: MutableMap<String, String>): HttpResponse<Map<*, *>> {
        // Create a request that will call POST /users endpoint on the server
        val request = HttpRequest.POST("/users", user)
        // Call the server
        return client.toBlocking().exchange(request, Map::class.java)!!
    }

    protected fun getUser(id: String): HttpResponse<Map<*, *>> {
        // Create a request that will call GET /users/{id} endpoint on the server
        val request = HttpRequest.GET<MutableMap<*, *>>("/users/$id")
        // Call the server
        return client.toBlocking().exchange(request, Map::class.java)!!
    }
}
