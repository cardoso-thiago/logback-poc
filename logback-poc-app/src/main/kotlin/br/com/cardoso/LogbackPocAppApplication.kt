package br.com.cardoso

import br.com.cardoso.model.Cliente
import net.logstash.logback.argument.StructuredArguments
import net.logstash.logback.marker.Markers
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class LogbackPocAppApplication

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(LogbackPocAppApplication::class.java)
    runApplication<LogbackPocAppApplication>(*args)
    MDC.put("correlationId", UUID.randomUUID().toString())
    logger.info("Log de info")
    logger.error("Log de erro")
    logger.debug("Log de debug")
    logger.info("Log info com structured arguments", StructuredArguments.keyValue("name_sa", "value_sa"))
    logger.info(Markers.append("name_markers", "value_markers"), "Log info com markers")
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
