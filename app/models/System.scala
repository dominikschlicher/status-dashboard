package models

sealed trait SystemStatus
object SystemStatus {
  def valueOf(value : String) = value match {case "RUNNING" => RUNNING case "DOWN" => DOWN}
}

object RUNNING extends SystemStatus {
  override def toString : String = "running"
}

object DOWN extends SystemStatus {
  override def toString : String = "down"
}

case class System(name: String, status: SystemStatus)
