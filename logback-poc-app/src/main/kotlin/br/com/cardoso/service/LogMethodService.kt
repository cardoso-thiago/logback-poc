package br.com.cardoso.service

import br.com.cardoso.annotation.LogAop
import org.springframework.stereotype.Service

@Service
class LogMethodService {

    @LogAop
    fun log() = "OK"
}