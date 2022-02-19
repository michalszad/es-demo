package es.template.esdemo.adapters.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/command")
class PaymentController {


    @PostMapping("/create-payment/v1")
    fun initialize(@RequestBody request: CreateRequest): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/initialize-payment/v1")
    fun initialize(@RequestBody request: InitializeRequest): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }

    @PostMapping("/complete-payment/v1")
    fun complete(@RequestBody request: CompleteRequest): ResponseEntity<String> {
        return ResponseEntity.ok("OK")
    }
}
