package blogs.blog

import com.scalaprog.domain.AggregateRoot
import commands.{StartNewBlogPost, CreateNewBlogCommand}
import java.util.UUID
import com.scalaprog.events.AbstractEvent
import BlogCreated

/**
 * User: soren
 */
class BlogsAggregateRoot(id: UUID) extends AggregateRoot(id) {

  def createNewBlog(cmd: CreateNewBlogCommand) {
    require(!cmd.name.isEmpty)
    applyEvent(BlogCreated(cmd.name))
  }

  def newPost(cmd: StartNewBlogPost) {
    require(!cmd.title.isEmpty)
    applyEvent(BlogCreated(cmd.title, cmd.data, cmd.draft), true)
  }

  def applyEvent(cmd: AbstractEvent, storeEvent: Boolean) {

  }
}
