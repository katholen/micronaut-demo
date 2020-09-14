package micronaut.demo.users

import java.util.*
import javax.inject.Singleton

@Singleton
class UsersMapRepository {
    private val users: MutableMap<String, User> = mutableMapOf()

    fun save(user: User): User {
        users[user.userName] = user
        return user
    }

    fun findById(id: String): Optional<User> {
        return Optional.of(users[id]!!)
    }
}
