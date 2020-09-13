package micronaut.demo.users

import java.util.*

interface UsersRepository {
    fun save(user: User): User

    fun findById(id: String): Optional<User>

    fun deleteAll()
}
