package com.scalaprog.domain

import java.util.UUID
import com.scalaprog.eventstore.EventStore

/**
 * User: soren
 */
class Repository[T <: AggregateRoot](eventStore: EventStore) extends IRepository[T] {

  def save(aggregate: AggregateRoot, expectedVersion: Int) {
    eventStore.save(aggregate.unCommitedEvent, aggregate.id)
  }

  def getById(id: UUID, c: Class[_ <: AggregateRoot]): T = {
    val obj = c.getConstructor(classOf[UUID]).newInstance(id) // get the constructor with an uuid, to determine class
    val events = eventStore.getEvents(id)
    obj.loadFromHistory(events)
    obj.asInstanceOf[T]
  }
}
