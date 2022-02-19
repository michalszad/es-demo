package es.template.esdemo.infrastructure

import es.template.domain.approach2.Payment

class PaymentFactory {

    fun createPayment(): Payment {
        return Payment()
    }
}
