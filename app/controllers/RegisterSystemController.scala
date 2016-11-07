package controllers


import models.{RegisteredSystem, SystemForm}
import play.api.mvc._
import services.RegisteredSystemService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class RegisterSystemController extends Controller {


  def index = Action.async {
    implicit request =>
      RegisteredSystemService.listAllSystems map { systems =>
        Ok(views.html.registerSystem(SystemForm.systemForm, systems))
      }
  }


  def addSystem = Action.async {
    implicit request => {
      SystemForm.systemForm.bindFromRequest.fold(
        errorForm => {
          RegisteredSystemService.listAllSystems map { systems =>
            Ok(views.html.registerSystem(errorForm, systems))
          }
        },
        serviceData => {
          val newService = RegisteredSystem(0, serviceData.name, serviceData.url)
          RegisteredSystemService.addSystem(newService).map(res => Redirect(routes.RegisterSystemController.index()))
        }
      )
    }
  }

}
