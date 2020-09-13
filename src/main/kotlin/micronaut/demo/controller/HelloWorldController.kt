package micronaut.demo.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue

@Controller("/hello")
class HelloWorldController {
    @Get
    fun helloWorldQueryParam(@QueryValue(value = "name", defaultValue = "World") name: String): HttpResponse<String> {
        return printName(name)
    }

    @Get("/{name}")
    fun helloWorldPathVariable(@PathVariable("name") name: String): HttpResponse<String> {
        return printName(name)
    }

    private fun printName(name: String): HttpResponse<String> {
        return HttpResponse.ok("Hello $name!")
    }
}
