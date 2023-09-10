package br.com.cardoso.controller

import br.com.cardoso.annotation.LogAop
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
class TestController2 {

    @LogAop
    @GetMapping("/test3")
    fun test3MethodAnnotation(): String {
        return "OK"
    }

    @LogAop
    @GetMapping("/test4")
    fun test4MethodAnnotation(): String {
        throw Exception("Erro na execução do test4")
    }
}
