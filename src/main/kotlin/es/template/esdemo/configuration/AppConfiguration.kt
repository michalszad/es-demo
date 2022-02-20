package es.template.esdemo.configuration

import es.template.esdemo.adapters.db.InMemoryPaymentRepository
import es.template.esdemo.adapters.handlers.CommandHandler
import es.template.esdemo.adapters.handlers.CompleteCommandHandler
import es.template.esdemo.adapters.handlers.CreateCommandHandler
import es.template.esdemo.adapters.handlers.InitializeCommandHandler
import es.template.esdemo.adapters.handlers.PaymentCommand
import es.template.esdemo.adapters.handlers.PaymentCommands
import es.template.esdemo.adapters.psp.TestPaymentProviderService
import es.template.esdemo.infrastructure.InMemoryBasedEventStore
import es.template.esdemo.query.ProjectionsBasedQueries
import es.template.esdemo.query.Query
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfiguration {

    private val eventStore = InMemoryBasedEventStore()

    @Bean
    fun paymentCommandHandlers(): Map<PaymentCommands, CommandHandler<PaymentCommand>> {
        val repository = InMemoryPaymentRepository(eventStore)
        val paymentProvider = TestPaymentProviderService()

        return mapOf(
            PaymentCommands.CREATE to CreateCommandHandler(repository),
            PaymentCommands.INITIALIZE to InitializeCommandHandler(repository, paymentProvider),
            PaymentCommands.COMPLETE to CompleteCommandHandler(repository)
        )
    }

    @Bean
    fun paymentQuery(): Query {
        return ProjectionsBasedQueries(eventStore)
    }

}
