package br.com.cardoso.service

import br.com.cardoso.annotation.LogAop
import org.springframework.stereotype.Service

@Service
@LogAop
class LogClassService {
    fun log() = "OK"
}