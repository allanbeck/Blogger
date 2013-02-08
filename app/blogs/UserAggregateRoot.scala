package blogs

import com.scalaprog.domain.AggregateRoot
import commands.{CreateNewBlogCommand, CreateUserCommand, StartNewBlogPost}
import events.{UserCreatedNewBlog, UserCreated, BlogCreated}
import java.util.UUID
import com.scalaprog.events.AbstractEvent
import com.scalaprog.engine.Server

/**
 * User: soren
 */
class UserAggregateRoot(id: UUID) extends AggregateRoot(id) {

  def createUser(cmd: CreateUserCommand) {
    applyEvent(UserCreated(cmd.name), true)
  }

  def createBlog(cmd: CreateNewBlogCommand) {
    applyEvent(UserCreatedNewBlog(cmd.id, cmd.name), true)
  }

  def applyEvent(cmd: AbstractEvent, storeEvent: Boolean) {
    unCommitedEvent = cmd :: unCommitedEvent
  }
}
