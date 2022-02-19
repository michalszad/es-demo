package es.template.esdemo.domain

import es.template.domain.CompletePaymentCommand
import es.template.domain.CreatePaymentCommand
import es.template.domain.InitializePaymentCommand
import es.template.domain.approach2.AggregateRoot
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

        fun create(command: CreatePaymentCommand): Payment {
            val payment = Payment()
            val event = PaymentCreatedEvent(
                paymentId = UUID.randomUUID().toString(),
                description = command.description
            )
            payment.applyEvent(event)
            return payment;
        }
    }

    fun initialize(command: InitializePaymentCommand) {
        val event = PaymentInitializedEvent(paymentId = paymentId)
        applyEvent(event)
    }

    fun complete(command: CompletePaymentCommand) {
        val event = PaymentCompletedEvent(paymentId = paymentId)
        applyEvent(event)
    }

    private fun on(event: PaymentCreatedEvent) {
        // mutate state here
        state = State.CREATED
        paymentId = event.paymentId
        description = event.description
    }

    private fun on(event: PaymentInitializedEvent) {
        // mutate state here
        state = State.INITIALIZED
    }

    private fun on(event: PaymentCompletedEvent) {
        // Mutate state here
        state = State.COMPLETED
    }

    override fun rehydrate(event: PaymentEvent) {
        when (event) {
            is PaymentCreatedEvent -> on(event)
            is PaymentInitializedEvent -> on(event)
            is PaymentCompletedEvent -> on(event)
            else -> {
                println("Omitted event")
                // Omit other events
            }
        }
    }
}
