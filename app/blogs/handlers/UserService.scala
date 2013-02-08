package blogs.handlers

import com.scalaprog.command.{AbstractCommand, CommandHandler}
import blogs.UserAggregateRoot
import com.scalaprog.domain.IRepository
import blogs.commands.CreateUserCommand
import java.util.UUID

/**
 * User: soren
 */
class UserService(repo: IRepository[UserAggregateRoot]) extends CommandHandler[UserAggregateRoot](repo){
  def handle(cmd: AbstractCommand) {

    cmd match {
      case c: CreateUserCommand => {
        val agg = new UserAggregateRoot(UUID.randomUUID())
          agg.createUser(c)
        repo.save(agg, agg.version)
      }
    }
  }
}
