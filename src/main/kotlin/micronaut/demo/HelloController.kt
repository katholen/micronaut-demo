package micronaut.demo

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue

@Controller("/hello")
class HelloController {
    @Get(produces = [MediaType.TEXT_PLAIN])
    fun helloWithName(@QueryValue(value = "name", defaultValue = "World") name: String): String {
        return "Hello $name!"
    }
}
