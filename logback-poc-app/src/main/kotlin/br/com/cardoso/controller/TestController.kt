package br.com.cardoso.controller

import br.com.cardoso.annotation.LogAop
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
@LogAop
class TestController {
    @GetMapping("/test")
    fun test(): String {
        return "OK"
    }

    @GetMapping("/test2")
    fun test2WithException(): String {
        throw Exception("Erro na execução do test2")
    }
}
