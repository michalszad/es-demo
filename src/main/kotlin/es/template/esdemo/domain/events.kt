package es.template.esdemo.domain

import java.time.Instant


sealed class PaymentEvent : Event {
    val created = Instant.now()
    abstract val paymentId: String
}

data class PaymentCreatedEvent(
    override val paymentId: String,
    val description: String
) : PaymentEvent()

data class PaymentInitializedEvent(
    override val paymentId: String
) : PaymentEvent()

data class PaymentCompletedEvent(
    override val paymentId: String,
) : PaymentEvent()
