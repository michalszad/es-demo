package es.template.domain

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

        @JvmStatic
        fun from(paymentId: Long) = PaymentAggregate(paymentId.toString())
    }
}


