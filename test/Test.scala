import blogs.commands.CreateUserCommand
import blogs.handlers.UserService
import blogs.UserAggregateRoot
import com.scalaprog.domain.Repository
import com.scalaprog.engine.Server
import com.scalaprog.eventstore.MemoryEventStore
import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * User: soren
 */
class Test extends Specification {
  "Creating user command should result in one event" in  {
    println("Testing!")
    //Server.eventStore = MemoryEventStore
    val storage = MemoryEventStore
    // Register the command handlers with the repositories
    Server.register(new UserService(new Repository[UserAggregateRoot](storage)))
    for(i <- 0 until 1000)
      Server.execute(CreateUserCommand("Søren"+i))

    assert(storage.getEventLog.size == 1000)
    assert(storage.getEventLog(0).toString.contains("UserCreated"))

    println(storage.getEventLog.mkString("\n"))
  }

}
