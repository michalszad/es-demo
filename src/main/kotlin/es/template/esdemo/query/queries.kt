package es.template.esdemo.query

import es.template.domain.PaymentAggregate
import es.template.esdemo.infrastructure.EventStore

interface Query {

    fun getPaymentResult(paymentId: String)
}

class ProjectionsBasedQueries(private val eventStore: EventStore) : Query {

    override fun getPaymentResult(paymentId: String) {
        val events = eventStore.findByAggregate(PaymentAggregate(paymentId))
        PaymentProjection.createFrom(events)
        TODO("Not yet implemented")
    }

}
