package controllers


import models.{DB, Person}
import play.api.data.Forms._
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._
import services.TwitterService


class Application extends Controller{
  def index = Action {
    Ok(views.html.index("Hello World!"))
  }


  val personForm: Form[Person] = Form {
    mapping(
      "name" -> text
    )(Person.apply)(Person.unapply)
  }

  def addPerson() = Action { implicit request =>
    val person = personForm.bindFromRequest.get
    DB.save(person)
    Redirect(routes.Application.index)

  }

  def getPersons = Action {
    val persons = DB.query[Person].fetch
    Ok(Json.toJson(persons))
  }
  def getMessages = Action {

    val messages = TwitterService.getTweets
    Ok(messages.toString)
  }
}