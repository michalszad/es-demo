package es.template.esdemo.adapters.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import es.template.esdemo.adapters.handlers.CompletePaymentCommand
import es.template.esdemo.adapters.handlers.CreatePaymentCommand
import es.template.esdemo.adapters.handlers.InitializePaymentCommand
import java.util.UUID

// Validation could be added here, or in specific resource
@JsonIgnoreProperties(ignoreUnknown = true)
class CreateRequest {
    var description: String? = null

    fun toCommand() = CreatePaymentCommand(paymentId = UUID.randomUUID().toString(), description = description!!)
}

@JsonIgnoreProperties(ignoreUnknown = true)
class InitializeRequest {
    var paymentId: String? = null

    fun toCommand() = InitializePaymentCommand(id = paymentId!!)
}

@JsonIgnoreProperties(ignoreUnknown = true)
class CompleteRequest {
    var paymentId: String? = null

    fun toCommand() = CompletePaymentCommand(id = paymentId!!)
}

data class PaymentResponse(
    val status: String,
    val paymentId: String? = null,
    val parameters: Map<String, Any> = emptyMap(),
    val errorContext: Map<String, Any> = emptyMap()
)
