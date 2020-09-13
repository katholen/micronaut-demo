package micronaut.demo.users

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import micronaut.demo.exceptions.AlreadyExistsException
import micronaut.demo.exceptions.NotFoundException
import javax.validation.Valid

@Validated
@Controller("/users")
class UsersController(
    private val usersRepository: UsersRepository
) {

    @Post
    fun create(
        @Valid @Body user: User
    ): HttpResponse<User> {
        val existingUser = usersRepository.findById(user.userName)
        if (existingUser.isPresent) {
            throw AlreadyExistsException()
        }
        usersRepository.save(user)
        return HttpResponse.created(user)
    }

    @Get("/{id}")
    fun findById(
        @Parameter("id") id: String
    ): HttpResponse<User> {
        val user = usersRepository.findById(id)
        if (user.isEmpty) {
            throw NotFoundException()
        }
        return HttpResponse.ok(user.get())
    }
}
