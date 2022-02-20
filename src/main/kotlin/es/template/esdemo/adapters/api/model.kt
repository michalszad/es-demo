package es.template.esdemo.adapters.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import es.template.esdemo.domain.CompletePaymentCommand
import es.template.esdemo.domain.CreatePaymentCommand
import es.template.esdemo.domain.InitializePaymentCommand
import es.template.esdemo.query.ErrorResult
import es.template.esdemo.query.Result
import es.template.esdemo.query.SuccessResult

// Validation could be added here, or in specific resource
@JsonIgnoreProperties(ignoreUnknown = true)
class CreateRequest {
    var description: String? = null

    fun toCommand() = CreatePaymentCommand(description = description!!)
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
