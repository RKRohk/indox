package com.rorosa.indox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class IndoxApplication

fun main(args: Array<String>) {
    runApplication<IndoxApplication>(*args)
}
