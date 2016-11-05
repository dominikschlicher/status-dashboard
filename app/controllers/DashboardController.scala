package controllers

import javax.inject.Singleton

import models.{DOWN, RUNNING, System, SystemView}
import play.api.mvc.{Action, Controller}

@Singleton
class DashboardController extends Controller {

  def index = Action {
    val systems : List[System] = List(System("Foo", RUNNING), System("Bar", DOWN))
    val systemViews = systems.map(system => SystemView(system))
    Ok(views.html.dashboard(systemViews))
  }
}
