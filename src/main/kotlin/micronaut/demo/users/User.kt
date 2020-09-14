package micronaut.demo.users

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected // this is necessary to mark the bean's properties as read/write without using reflection
data class User(
    @field:NotBlank // Requires that the field must not be null and must contain at least one non-whitespace character. Note: Because we are using kotlin and setting the type as non-nullable, micronaut will return a 400 already if the user does not supply this value
    val userName: String,
    @field:NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val password: String
)
