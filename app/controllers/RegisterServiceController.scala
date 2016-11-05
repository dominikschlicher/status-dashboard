package controllers


import models.DashboardService
import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages.Implicits._
import play.api.mvc._

class RegisterServiceController extends Controller {


  val serviceForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "url" -> nonEmptyText
    )(DashboardService.apply)(DashboardService.unapply)
  )


  def index = Action {
    Ok(views.html.registerService(serviceForm))
  }


  def handleRequest = Action {
    implicit request => {
      serviceForm.bindFromRequest.fold(
        formWithErrors => {
          // binding failure, you retrieve the form containing errors:
          BadRequest(views.html.registerService(formWithErrors)) // kein BadRequest
        },
        serviceData => {
          /* binding success, you get the actual value. */
          val newService = models.DashboardService(serviceData.name, serviceData.url)
          Redirect("/dashboard")
        }
      )
    }
  }


  val servicePost = Action(parse.form(serviceForm)) { implicit request =>
    val serviceData = request.body
    val newService = models.DashboardService(serviceData.name, serviceData.url)
    Redirect("/dashboard")
  }

}
