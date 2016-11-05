package controllers


import models.{RegisteredSystem, SystemForm}
import play.api.mvc._
import services.SystemService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RegisterSystemController extends Controller {


  def index = Action.async {
    implicit request =>
      SystemService.listAllSystems map { systems =>
        Ok(views.html.registerSystem(SystemForm.systemForm, systems))
      }
  }


  def addSystem = Action.async {
    implicit request => {
      SystemForm.systemForm.bindFromRequest.fold(
        errorForm => {
          SystemService.listAllSystems map { systems =>
            Ok(views.html.registerSystem(errorForm, systems))
          }
        },
        serviceData => {
          val newService = RegisteredSystem(0, serviceData.name, serviceData.url)
          SystemService.addSystem(newService).map(res => Redirect(routes.RegisterSystemController.index()))
        }
      )
    }
  }

}
