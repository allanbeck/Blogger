package blogs.commands

import com.scalaprog.command.AbstractCommand

/**
 * User: soren
 */
case class StartNewBlogPost(title: String, data: String, draft: Boolean) extends AbstractCommand {

}
