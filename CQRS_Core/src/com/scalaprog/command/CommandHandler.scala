package com.scalaprog.command

import java.util.UUID
import com.scalaprog.engine.Server
import com.scalaprog.domain.{IRepository, AggregateRoot}
import java.lang.reflect.ParameterizedType

/**
 * User: soren
 */
abstract class CommandHandler[A <: AggregateRoot](repository: IRepository[A]) {

  def handle(cmd: AbstractCommand)

  def getAggreateById[A](id: UUID, c: Class[_ <: AggregateRoot]) = {
    val obj = c.getConstructor(classOf[UUID]).newInstance(id)
    val c1 = obj.getClass
    val t = c.getClass.asInstanceOf[ParameterizedType].getActualTypeArguments()(0)
    repository.getById(id, c1)
  }
}
