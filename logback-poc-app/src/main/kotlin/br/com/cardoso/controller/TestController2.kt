package br.com.cardoso.controller

import br.com.cardoso.annotation.LogAop
import org.springframework.http.HttpMethod.GET
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
class TestController2 {

    @LogAop
    @RequestMapping(method = [RequestMethod.GET], path = ["/test3"])
    fun test3MethodAnnotation(): String {
        return "OK"
    }

    @LogAop
    @GetMapping("/test4")
    fun test4MethodAnnotation(): String {
        throw Exception("Erro na execução do test4")
    }

    @LogAop
    @PostMapping("/test5/post/longer/path")
    fun test5PostLongerPath(): String {
        throw Exception("Erro na execução do test5")
    }
}
