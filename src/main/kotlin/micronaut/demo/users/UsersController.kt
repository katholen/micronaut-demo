package micronaut.demo.users

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.validation.Valid

@Validated
@Controller("/users")
class UsersController(
    private val usersMapRepository: UsersMapRepository // Inject the Singleton of UsersMapRepository
) {

    @Post
    fun create(
        @Valid @Body user: User
    ): HttpResponse<User> {
        usersMapRepository.save(user) // Update to actually save the user
        return HttpResponse.created(user)
    }

    @Get("/{id}") // Specifies the GET /users/{id} endpoint
    fun findById(
        @Parameter("id") id: String // Define a path parameter named id and injects it into the method from the URL path
    ): HttpResponse<User> {
        return HttpResponse.ok(usersMapRepository.findById(id).get()) // Find the user by id and return it to the client
    }
}
