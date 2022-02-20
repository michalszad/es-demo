package es.template.esdemo.query


import es.template.esdemo.domain.Event
import es.template.esdemo.domain.Payment
import es.template.esdemo.domain.PaymentCompletedEvent
import es.template.esdemo.domain.PaymentCreatedEvent
import es.template.esdemo.domain.PaymentInitializedEvent

interface Projection

class PaymentProjection : Projection {

    lateinit var id: String
    lateinit var description: String
    lateinit var state: Payment.State

    companion object {

        fun createFrom(events: List<Event>): PaymentProjection {
            val projection = PaymentProjection()
            events.forEach { event -> projection.rehydrate(event) }
            return projection
        }
    }

    private fun on(event: PaymentCreatedEvent) {
        id = event.paymentId
        state = Payment.State.CREATED
        description = event.description
    }

    private fun on(event: PaymentInitializedEvent) {
        state = Payment.State.INITIALIZED
    }

    private fun on(event: PaymentCompletedEvent) {
        state = Payment.State.COMPLETED
    }

    private fun rehydrate(event: Event) {
        when (event) {
            is PaymentCreatedEvent -> on(event)
            is PaymentInitializedEvent -> on(event)
            is PaymentCompletedEvent -> on(event)
            else -> {
                // Omit event
            }
        }
    }
}
