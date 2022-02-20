package es.template.esdemo.domain

import java.util.UUID

interface Message

interface Command : Message

interface Event : Message

interface PaymentCommand : Command

data class CreatePaymentCommand(val description: String) : PaymentCommand
data class InitializePaymentCommand(val id: String) : PaymentCommand
data class CompletePaymentCommand(val id: String) : PaymentCommand

abstract class Aggregate(val type: String, val id: String)

data class PaymentAggregate(val identifier: String) : Aggregate(AGGREGATE_TYPE, identifier) {
    companion object {
        const val AGGREGATE_TYPE = "Payment"
    }
}


data class IdempotencyToken(
    val paymentId: String,
    val paymentOperationId: String
) {
    override fun toString() = "$paymentId-$paymentOperationId"

    companion object {
        @JvmStatic
        private val REGEX = """(\d+)-(\d+)""".toRegex()

        @JvmStatic
        fun random() = IdempotencyToken(UUID.randomUUID().toString(), UUID.randomUUID().toString())

        @JvmStatic
        fun from(value: String) =
            if (REGEX.matches(value)) {
                val matchResult = REGEX.find(value)
                val (paymentId, paymentOperationId) = matchResult!!.destructured
                IdempotencyToken(paymentId, paymentOperationId)
            } else {
                throw IllegalArgumentException("Idempotency token cannot be built from String: $value")
            }
    }
}


