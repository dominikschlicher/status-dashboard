package controllers


import models.RegisteredSystem
import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages.Implicits._
import play.api.mvc._

class RegisterSystemController extends Controller {


  val systemForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "url" -> nonEmptyText
    )(RegisteredSystem.apply)(RegisteredSystem.unapply)
  )


  def index = Action {
    Ok(views.html.registerSystem(systemForm))
  }


  def handleRequest = Action {
    implicit request => {
      systemForm.bindFromRequest.fold(
        formWithErrors => {
          // binding failure, you retrieve the form containing errors:
          BadRequest(views.html.registerSystem(formWithErrors)) // kein BadRequest
        },
        serviceData => {
          /* binding success, you get the actual value. */
          val newService = models.RegisteredSystem(serviceData.name, serviceData.url)
          Redirect("/dashboard")
        }
      )
    }
  }


  val servicePost = Action(parse.form(systemForm)) { implicit request =>
    val serviceData = request.body
    val newService = models.RegisteredSystem(serviceData.name, serviceData.url)
    Redirect("/dashboard")
  }

}
