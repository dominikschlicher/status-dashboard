package models


case class SystemView(system: System) {
  def statusCssClass : String = if (system.status ==  RUNNING) "success" else "danger"
  def displayName : String = system.name
  def status : String = system.status.toString
}