package es.template.esdemo.adapters.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

// Validation could be added here, or in specific resource
@JsonIgnoreProperties(ignoreUnknown = true)
class CreateRequest {
    var paymentId: String? = null
    var description: String? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class InitializeRequest {
    var paymentId: String? = null
}

@JsonIgnoreProperties(ignoreUnknown = true)
class CompleteRequest {
    var paymentId: String? = null
}

sealed class Result {
    abstract val status: String
    abstract val paymentId: String?
}

data class SuccessResponse(
    override val status: String,
    override val paymentId: String? = null,
    val parameters: Map<String, Any> = emptyMap()
) : Result()

data class ErrorResult(
    override val status: String,
    override val paymentId: String? = null,
    val errorMsg: String? = null
) : Result()
