package com.scalaprog.eventstore

import com.mongodb.{MongoURI, BasicDBObject, Mongo}
import com.scalaprog.events.AbstractEvent
import java.lang.Exception
import java.util.{Date, UUID}
import com.scalaprog.engine.ProjectionEngine

import com.codahale.jerkson.Json._
import com.codahale.jerkson.AST.JObject

/**
 * Mongo driver for the eventstore.
 */

object MongoEventStore extends EventStore {

  val db = {
    val url = System.getenv("MONGOHQ_URL")
    if (url == null || url.isEmpty) {
      new Mongo().getDB("eventStore")
    } else {
      val mongoURI = new MongoURI(System.getenv("MONGOHQ_URL"));
      mongoURI.connectDB()
    }

  }

  val coll = db.getCollection("events")

  def save(event: AbstractEvent, id: UUID) {
    val doc = new BasicDBObject()
    doc.put("id", id)

    doc.put("eventClass", event.getClass().getName())
    val json = generate(event)
    doc.put("event", json)
    doc.put("date", new Date())
    coll.insert(doc)

    ProjectionEngine.publishEvent(event)
  }

  def deserializeEvent(json: String, eventType: String) = {
    //mapper.readValue(json, Class.forName(eventType)).asInstanceOf[AbstractEvent]
    val c = Class.forName(eventType)
    parse[JObject](json).asInstanceOf[AbstractEvent]
  }

  def getEvents(aggregateId: UUID): List[AbstractEvent] = {
    var events = List[AbstractEvent]()
    try {
      val coll = db.getCollection("events")

      val query = new BasicDBObject()
      query.put("id", aggregateId)

      val cursor = coll.find(query)

      try {
        while (cursor.hasNext()) {
          val next = cursor.next()
          val eventClassStr = next.get("eventClass").toString()
          val event = next.get("event").toString()
          val eventObj = deserializeEvent(event, eventClassStr) // gson.fromJson(event, Class.forName(eventClassStr)).asInstanceOf[AbstractEvent]

          events = eventObj :: events
        }
      } finally {
        cursor.close()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    return events
  }


  def getEventLog: List[AbstractEvent] = {
    var events = List[AbstractEvent]()
    try {
      val coll = db.getCollection("events")

      val query = new BasicDBObject()

      val cursor = coll.find(query)

      try {
        while (cursor.hasNext()) {
          val next = cursor.next()
          val eventClassStr = next.get("eventClass").toString()
          val event = next.get("event").toString()

          val eventObj = deserializeEvent(event, eventClassStr)
          events = eventObj :: events
        }
      } finally {
        cursor.close()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
    return events
  }

  def clear{ // doesn't make sence in mongo
  }

  def save(events: List[AbstractEvent], id: UUID) {
    for(event <- events)
      save(event, id)
  }
}

