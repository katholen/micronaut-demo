package micronaut.demo

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class MicronautDemoTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }

}
