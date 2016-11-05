package models

sealed trait SystemStatus

object RUNNING extends SystemStatus {
  override def toString : String = "running"
}

object DOWN extends SystemStatus {
  override def toString : String = "down"
}

case class System(name: String, status: SystemStatus)