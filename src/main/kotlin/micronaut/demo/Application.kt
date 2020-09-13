package micronaut.demo

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
	build()
		.args(*args)
		.packages("micronaut.demo")
		.start()
}

