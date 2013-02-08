package blogs.commands

import com.scalaprog.command.AbstractCommand
import java.util.UUID

/**
 * User: soren
 */
case class CreateNewBlogCommand(id: UUID, name: String) extends AbstractCommand {

}
