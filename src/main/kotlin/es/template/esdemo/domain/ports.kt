package es.template.esdemo.domain

interface AggregateRepository<T> {
    fun findById(id: String): T?
    fun store(aggregate: T)
    fun create(): T
}

interface PaymentExternalService
