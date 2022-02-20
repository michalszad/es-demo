package es.template.esdemo.query

import es.template.esdemo.domain.Payment.State.COMPLETED
import es.template.esdemo.domain.Payment.State.COMPLETED_FAILED
import es.template.esdemo.domain.Payment.State.COMPLETED_UNKNOWN
import es.template.esdemo.domain.Payment.State.CREATED
import es.template.esdemo.domain.Payment.State.INITIALIZED
import es.template.esdemo.domain.Payment.State.INITIALIZE_FAILED
import es.template.esdemo.domain.PaymentAggregate
import es.template.esdemo.infrastructure.EventStore

interface Query {

    fun getPaymentResult(paymentId: String): Result
}

class ProjectionsBasedQueries(private val eventStore: EventStore) : Query {

    override fun getPaymentResult(paymentId: String): Result {
        val events = eventStore.findByAggregate(PaymentAggregate(paymentId))
        val payment = PaymentProjection.createFrom(events)
        return when (payment.state) {
            CREATED, INITIALIZED, COMPLETED -> SuccessResult(
                status = "SUCCESS",
                paymentId = payment.id,
                parameters = mapOf("description" to payment.description)
            )
            COMPLETED_UNKNOWN -> ErrorResult(
                status = "UNKNOWN",
                paymentId = payment.id,
                errorMsg = "Unknown error"
            )
            INITIALIZE_FAILED, COMPLETED_FAILED -> ErrorResult(
                status = "FAILED",
                paymentId = payment.id,
                errorMsg = "Get from event"
            )
            else -> ErrorResult(
                status = "FAILED",
                errorMsg = "Unexpected error"
            )
        }
    }
}

sealed class Result {
    abstract val status: String
    abstract val paymentId: String?
}

data class SuccessResult(
    override val status: String,
    override val paymentId: String? = null,
    val parameters: Map<String, Any> = emptyMap()
) : Result()

data class ErrorResult(
    override val status: String,
    override val paymentId: String? = null,
    val errorMsg: String? = null,
    val errorContext: Map<String, Any> = emptyMap()
) : Result()

