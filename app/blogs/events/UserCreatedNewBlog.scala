package blogs.events

import com.scalaprog.events.AbstractEvent
import java.util.UUID

/**
 * User: soren
 */
case class UserCreatedNewBlog(id: UUID, name: String) extends AbstractEvent {

}
