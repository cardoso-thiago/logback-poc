package br.com.cardoso

import br.com.cardoso.model.Cliente
import net.logstash.logback.argument.StructuredArguments
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
    logger.debug("Log de debug")
    logger.info("Log info com structured arguments", StructuredArguments.keyValue("name", "value"))
    logger.error(
        "Log error com structured arguments e objeto complexo", StructuredArguments.keyValue(
            "cliente", Cliente("Thiago", "333.333.333-33", "11.111.111/0001-01", "Rua dos Bobos, 0")
        )
    )
    try {
        throw Exception("Erro qualquer")
    } catch (e: Exception) {
        logger.error("Log de erro com exception", e)
    }
}
