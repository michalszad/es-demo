package es.template.esdemo.domain

import java.util.UUID

class Payment : AggregateRoot<PaymentEvent>() {

    enum class State {
        NEW,
        CREATED,
        INITIALIZED,
        INITIALIZE_FAILED,
        COMPLETED,
        COMPLETED_FAILED,
        COMPLETED_UNKNOWN
    }

    private var state = State.NEW
    private lateinit var paymentId: String
    private lateinit var description: String

    companion object {

        @JvmStatic
        fun create(paymentId: String, description: String): Payment {
            val payment = Payment()
            val event = PaymentCreatedEvent(
                paymentId = paymentId,
                description = description
            )
            payment.applyEvent(event)
            return payment
        }
    }

    fun initialize() {
        val event = PaymentInitializedEvent(paymentId = paymentId)
        applyEvent(event)
    }

    fun complete() {
        val event = PaymentCompletedEvent(paymentId = paymentId)
        applyEvent(event)
    }

    private fun on(event: PaymentCreatedEvent) {
        state = State.CREATED
        paymentId = event.paymentId
        description = event.description
    }

    private fun on(event: PaymentInitializedEvent) {
        state = State.INITIALIZED
    }

    private fun on(event: PaymentCompletedEvent) {
        state = State.COMPLETED
    }

    override fun rehydrate(event: PaymentEvent) {
        when (event) {
            is PaymentCreatedEvent -> on(event)
            is PaymentInitializedEvent -> on(event)
            is PaymentCompletedEvent -> on(event)
        }
    }
}
