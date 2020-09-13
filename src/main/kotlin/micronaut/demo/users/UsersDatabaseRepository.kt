package micronaut.demo.users

import io.micronaut.context.annotation.Requires
import io.micronaut.data.annotation.Query
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.repository.CrudRepository
import java.util.*


@JdbcRepository
@Requires(env = ["database"])
interface UsersDatabaseRepository : UsersRepository, CrudRepository<User, String> {
    @Query("SELECT * FROM users WHERE user_name = LOWER(:id)")
    override fun findById(id: String): Optional<User>
}
