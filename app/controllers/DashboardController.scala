package controllers

import javax.inject.{Inject, Singleton}

import models.{System, SystemStatus, SystemView}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsArray, JsPath, Reads}
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}

// Combinator syntax

@Singleton
class DashboardController @Inject()(implicit context: ExecutionContext, ws: WSClient) extends Controller {

  def index = Action.async {

    implicit val systemReads: Reads[System] = (
      (JsPath \ "name").read[String] and
        (JsPath \ "status").read[String].map(value => SystemStatus.valueOf(value))
      ) (System.apply _)

    val futureResult: Future[JsArray] = ws.url("http://localhost:9000/api/systems").get().map {
      response => response.json.as[JsArray]
    }

    val futureSystems = futureResult.map[List[System]] {
      jsArray => jsArray.value.map(value => value.as[System]).toList
    }

    val futureSystemViews = futureSystems.map(systems => systems.map(system => SystemView(system)))
    futureSystemViews.map(systemViews => Ok(views.html.dashboard(systemViews)))
  }
}
