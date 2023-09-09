package br.com.cardoso.masking

import br.com.cardoso.masking.impl.BaseMask
import org.springframework.core.env.Environment

class MyCustomMask(environment: Environment?) : BaseMask(environment) {
    override fun getType(): String {
        return "custom"
    }

    override fun mask(value: Any): Any {
        return "XoX"
    }
}
