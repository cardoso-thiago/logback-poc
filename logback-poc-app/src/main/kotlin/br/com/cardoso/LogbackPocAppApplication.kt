package br.com.cardoso

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LogbackPocAppApplication

fun main(args: Array<String>) {
	val logger = LoggerFactory.getLogger(LogbackPocAppApplication::class.java)
	runApplication<LogbackPocAppApplication>(*args)
	logger.info("Log de info")
	logger.error("Log de erro")
}
