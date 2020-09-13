package micronaut.demo.users

import javax.inject.Singleton

@Singleton
class UsersRepository {
    private val users: MutableMap<String, User> = mutableMapOf()

    fun save(user: User) {
        users[user.userName] = user
    }

    fun findById(id: String): User {
        return users[id]!!
    }
}
