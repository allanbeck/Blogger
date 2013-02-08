package controllers

import blogs.BlogsAggregateRoot
import java.util.UUID
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

object Application extends Controller {

  val createPostForm = Form(
    tuple(
      "title" -> nonEmptyText,
      "data" -> text
    )
  )

  def index = Action {
    val aggr = new BlogsAggregateRoot(UUID.randomUUID())
    Ok(views.html.index("Your new application is ready."))
  }

  def savePost = Action { implicit request =>
    println(request.body.asFormUrlEncoded)
    println(new String(request.body.asRaw.get.asBytes().get))
    Ok("saved")
  }
  def createPost = Action {
    val aggr = new BlogsAggregateRoot(UUID.randomUUID())
    createPostForm.fold(
      formWithErrors => {// binding failure, you retrieve the form containing errors,
        println("Errors")
        BadRequest(views.html.createPost("Error in post", formWithErrors))
      },
      value => {
        println("no errors")
        Ok(views.html.createPost("Creating new post", createPostForm))
      }
    )

  }

}