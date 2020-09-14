package micronaut.demo.exceptions

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import io.micronaut.http.simple.SimpleHttpResponseFactory

@Factory
class ExceptionHandlers {
    @Bean
    fun notFoundExceptionHandler(): ExceptionHandler<NotFoundException, HttpResponse<ErrorMessageResponse>> {
        return ExceptionHandler { _, _ ->
            HttpResponse.notFound(ErrorMessageResponse("Resource not found"))
        }
    }

    @Bean
    fun alreadyExistsExceptionHandler(): ExceptionHandler<AlreadyExistsException, HttpResponse<ErrorMessageResponse>> {
        return ExceptionHandler { _, _ ->
            SimpleHttpResponseFactory.INSTANCE.status(
                HttpStatus.CONFLICT,
                ErrorMessageResponse("Resource already exists")
            )
        }
    }
}
