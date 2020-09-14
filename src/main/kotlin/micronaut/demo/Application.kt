package micronaut.demo

import io.micronaut.runtime.Micronaut.build
import io.swagger.v3.oas.annotations.OpenAPIDefinition

@OpenAPIDefinition
object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        build()
            .args(*args)
            .packages("micronaut.demo")
            .start()
    }
}

