package com.ryandev.codevassignment2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class Codevassignment2Application

fun main(args: Array<String>) {
	runApplication<Codevassignment2Application>(*args)
}
