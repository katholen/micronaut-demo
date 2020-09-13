package micronaut.demo.users

import io.micronaut.context.annotation.Requires
import java.util.*
import javax.inject.Singleton

@Singleton
@Requires(env = ["map"])
class UsersMapRepository : UsersRepository {
    private val users: MutableMap<String, User> = mutableMapOf()

    override fun save(user: User): User {
        users[user.userName] = user
        return user
    }

    override fun findById(id: String): Optional<User> {
        val lowercaseId = id.toLowerCase()
        if (users.containsKey(lowercaseId)) {
            return Optional.of(users[lowercaseId]!!)
        }
        return Optional.empty()
    }

    override fun deleteAll() {
        users.clear()
    }
}
