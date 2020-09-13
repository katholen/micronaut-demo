package micronaut.demo.users

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.netty.DefaultHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.PostgreSQLContainer
import javax.inject.Inject

@MicronautTest(
    environments = ["database", "test"]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class BaseUsersControllerTest : TestPropertyProvider {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    lateinit var usersRepository: UsersRepository

    private lateinit var client: HttpClient
    private val postgreSQLContainer: PostgreSQLContainer<Nothing> = PostgreSQLContainer()

    @BeforeEach
    fun beforeEach() {
        client = DefaultHttpClient(server.url)
    }

    @AfterEach
    fun teardown() {
        client.close()
        usersRepository.deleteAll()
    }

    override fun getProperties(): Map<String, String> {
        postgreSQLContainer.start()
        return mapOf("datasources.default.url" to postgreSQLContainer.jdbcUrl)
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
