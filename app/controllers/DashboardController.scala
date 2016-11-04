package controllers

import javax.inject.Singleton

import models.System
import play.api.mvc.{Action, Controller}

@Singleton
class DashboardController extends Controller {

  def index = Action {
    val systems : List[System] = List(System("Foo", "OK"), System("Bar", "DOWN"))
    Ok(views.html.dashboard(systems))
  }
}
