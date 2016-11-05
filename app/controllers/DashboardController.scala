package controllers

import javax.inject.{Inject, Singleton}

import models.{System, SystemStatus, SystemView}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsArray, JsPath, Reads}
import play.api.libs.ws.{WSClient, WSResponse}
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

    for (
      response <- ws.url("http://localhost:9000/api/systems").get();
      jsArray <- Future.successful(response.json.as[JsArray]);
      systemList <- Future.successful(jsArray.value.map(value => value.as[System]).toList);
      systemsView <- Future.successful(systemList.map(system => SystemView(system)))
    ) yield Ok(views.html.dashboard(systemsView))
  }
}
