package es.template.esdemo.domain

abstract class AggregateRoot<E : Event> {

    private val changes = mutableListOf<E>()

    fun pullChanges(): List<E> {
        val changes = changes.toList()
        this.changes.clear()
        return changes
    }

    // Load from history
    fun rehydrateFrom(history: List<E>) {
        history.forEach { apply(it) }
    }

    protected fun applyEvent(event: E) {
        apply(event)
        add(event)
    }

    private fun apply(event: E) {
        rehydrate(event)
    }

    protected abstract fun rehydrate(event: E)

    private fun add(event: E) {
        changes.add(event)
    }
}
