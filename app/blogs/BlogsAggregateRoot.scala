package blogs

import com.scalaprog.domain.AggregateRoot
import commands.StartNewBlogPost
import events.BlogCreated
import java.util.UUID
import com.scalaprog.events.AbstractEvent

/**
 * User: soren
 */
class BlogsAggregateRoot(id: UUID) extends AggregateRoot(id) {

  def startNewBlogPost(cmd: StartNewBlogPost) {
    require(!cmd.title.isEmpty)
    applyEvent(BlogCreated(cmd.title, cmd.data, cmd.draft), true)
  }

  def applyEvent(cmd: AbstractEvent, storeEvent: Boolean) {

  }
}
