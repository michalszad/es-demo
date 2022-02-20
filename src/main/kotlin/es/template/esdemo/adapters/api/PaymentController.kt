package es.template.esdemo.adapters.api

import es.template.esdemo.adapters.handlers.CommandHandler
import es.template.esdemo.adapters.handlers.PaymentCommands
import es.template.esdemo.domain.PaymentCommand
import es.template.esdemo.query.ErrorResult
import es.template.esdemo.query.Query
import es.template.esdemo.query.Result
import es.template.esdemo.query.SuccessResult
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/command")
class PaymentController(
    private val paymentHandlers: Map<PaymentCommands, CommandHandler<PaymentCommand>>,
    private val paymentQuery: Query
) {

    @PostMapping("/create-payment/v1")
    fun create(@RequestBody request: CreateRequest): ResponseEntity<PaymentResponse> {
        paymentHandlers[PaymentCommands.CREATE]!!.handle(request.toCommand())
        val result = paymentQuery.getPaymentResult("hmmm")
        return ResponseEntity.ok(result.toPaymentResponse())
    }

    @PostMapping("/initialize-payment/v1")
    fun initialize(@RequestBody request: InitializeRequest): ResponseEntity<PaymentResponse> {
        paymentHandlers[PaymentCommands.INITIALIZE]!!.handle(request.toCommand())
        val result = paymentQuery.getPaymentResult(request.paymentId!!)
        return ResponseEntity.ok(result.toPaymentResponse())
    }

    @PostMapping("/complete-payment/v1")
    fun complete(@RequestBody request: CompleteRequest): ResponseEntity<PaymentResponse> {
        paymentHandlers[PaymentCommands.COMPLETE]!!.handle(request.toCommand())
        val result = paymentQuery.getPaymentResult(request.paymentId!!)
        return ResponseEntity.ok(result.toPaymentResponse())
    }

    private fun Result.toPaymentResponse(): PaymentResponse =
        when (this) {
            is SuccessResult -> {
                PaymentResponse(
                    status = this.status,
                    paymentId = this.paymentId,
                    parameters = this.parameters
                )
            }
            is ErrorResult -> {
                PaymentResponse(
                    status = this.status,
                    paymentId = this.paymentId,
                    errorContext = this.errorContext
                )
            }
        }
}
