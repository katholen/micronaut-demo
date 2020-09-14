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
        val lowercaseId = id.toLowerCase()
        if (users.containsKey(lowercaseId)) {
            return Optional.of(users[lowercaseId]!!)
        }
        return Optional.empty()
    }

    fun deleteAll() {
        users.clear()
    }
}
