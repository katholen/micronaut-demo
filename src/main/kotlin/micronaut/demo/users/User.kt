package micronaut.demo.users

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.jdbc.annotation.ColumnTransformer
import javax.validation.constraints.NotBlank

@Introspected
@MappedEntity("users")
data class User(
    @field:Id
    @field:NotBlank
    @ColumnTransformer(write = "LOWER(?)")
    val userName: String,
    @field:NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String
)
