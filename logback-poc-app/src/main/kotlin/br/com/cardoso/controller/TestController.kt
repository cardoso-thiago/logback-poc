package br.com.cardoso.controller

import br.com.cardoso.annotation.LogAop
import br.com.cardoso.model.GenericModel
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors


@RestController
@LogAop
class TestController {
    @GetMapping("/test")
    fun test(): String {
        val logger = LoggerFactory.getLogger("TesteLogger")
        val listGenericModel = ArrayList<GenericModel>()
        listGenericModel.add(GenericModel("nome", "Thiago"))
        listGenericModel.add(GenericModel("cpf", "333.333.333-33"))
        listGenericModel.add(GenericModel("endereco", "Rua rua, 123"))

        val map = listGenericModel.stream().collect(Collectors.toMap(GenericModel::key, GenericModel::value))

        logger.atInfo().addKeyValue("entradas", map).log("Log com ofuscação complexa")
        return "OK"
    }

    @GetMapping("/test2")
    fun test2WithException(): String {
        throw Exception("Erro na execução do test2")
    }
}
