package es.template.esdemo.adapters.db

import es.template.esdemo.domain.AggregateRepository
import es.template.esdemo.domain.Payment
import es.template.esdemo.domain.PaymentAggregate


import es.template.esdemo.domain.PaymentEvent
import es.template.esdemo.infrastructure.EventStore


class InMemoryPaymentRepository(private val eventStore: EventStore) : AggregateRepository<Payment> {

    companion object {
        private val PAYMENT_NOT_FOUND: Payment? = null
    }

    override fun findById(id: String): Payment? {
        val history = eventStore
            .findByAggregate(PaymentAggregate(id))
            .filterIsInstance<PaymentEvent>()

        return if (history.isNotEmpty()) {
            val payment = Payment()
            payment.rehydrateFrom(history)
            payment
        } else PAYMENT_NOT_FOUND
    }

    override fun store(aggregate: Payment) {
        aggregate.pullChanges().forEach { eventStore.store(PaymentAggregate(it.paymentId), it) }
    }

    override fun create(): Payment {
        return Payment()
    }
}
