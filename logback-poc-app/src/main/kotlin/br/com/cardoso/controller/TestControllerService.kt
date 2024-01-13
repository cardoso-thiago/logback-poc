package br.com.cardoso.controller

import br.com.cardoso.service.LogClassService
import br.com.cardoso.service.LogMethodService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestControllerService(val logMethodService: LogMethodService, val logClassService: LogClassService) {

    @GetMapping("/testMethodService")
    fun testMethodService() = logMethodService.log()

    @GetMapping("/testClassService")
    fun testClassService() = logClassService.log()
}