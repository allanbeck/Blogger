package blogs.events

import com.scalaprog.events.AbstractEvent

/**
 * User: soren
 */
case class BlogCreated(title: String, text: String, draft: Boolean) extends AbstractEvent {

}
