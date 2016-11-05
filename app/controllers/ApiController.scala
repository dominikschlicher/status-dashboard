package controllers

import javax.inject.Singleton

import play.api.libs.json._
import play.api.mvc.{Action, Controller}

@Singleton
class ApiController extends Controller {

  def systems = Action {
    val json: JsValue = JsArray(Seq(
      JsObject(Seq(
        "name" -> JsString("Foo"),
        "status" -> JsString("RUNNING")
      )),
      JsObject(Seq(
        "name" -> JsString("Bar"),
        "status" -> JsString("DOWN")
      ))
    ))
    Ok(json)
  }

}
