package services

import models.{RegisteredSystem, RegisteredSystems}

import scala.concurrent.Future


object RegisteredSystemService {

  def addSystem(system: RegisteredSystem): Future[String] = {
    RegisteredSystems.add(system)
  }

  def listAllSystems: Future[Seq[RegisteredSystem]] = {
    RegisteredSystems.listAll

  }
}
