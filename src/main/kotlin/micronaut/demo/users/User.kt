package micronaut.demo.users

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class User(
    @field:NotBlank
    val userName: String,
    @field:NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String
)
