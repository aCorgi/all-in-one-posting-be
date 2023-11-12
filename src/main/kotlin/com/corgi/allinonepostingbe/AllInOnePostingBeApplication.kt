package com.corgi.allinonepostingbe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class AllInOnePostingBeApplication

fun main(args: Array<String>) {
    runApplication<AllInOnePostingBeApplication>(*args)
}
