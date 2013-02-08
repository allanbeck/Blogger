package blogs.events

import com.scalaprog.events.AbstractEvent

/**
 * User: soren
 */
case class UserCreated(name: String) extends AbstractEvent {

}
