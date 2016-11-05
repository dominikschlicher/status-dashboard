package models


import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


case class RegisteredSystem(id: Long, name: String, url: String)

case class RegisteredSystemForm(name: String, url: String)


object SystemForm {
  val systemForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "url" -> nonEmptyText
    )(RegisteredSystemForm.apply)(RegisteredSystemForm.unapply)
  )
}


class RegisteredSystemDef(tag: Tag) extends Table[RegisteredSystem](tag, "registeredSystem") {


  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def url = column[String]("url")

  override def * = (id, name, url) <> (RegisteredSystem.tupled, RegisteredSystem.unapply)

}


object RegisteredSystems {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val sytems = TableQuery[RegisteredSystemDef]

  def add(registeredSystem: RegisteredSystem): Future[String] = {
    dbConfig.db.run(sytems += registeredSystem).map(res => "System sucessfullyadded").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def listAll: Future[Seq[RegisteredSystem]] = {
    dbConfig.db.run(sytems.result)
  }

}


