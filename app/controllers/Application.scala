package controllers


import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._
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

  val searchForm: Form[Hashtag] = Form {
    mapping(
      "hashtag" -> text
    )(Hashtag.apply)(Hashtag.unapply)
  }

  def addPerson() = Action { implicit request =>
    val person = personForm.bindFromRequest.get
    DB.save(person)
    Redirect(routes.Application.index)
  }

  def SearchByHashtag() = Action { implicit request =>
    val hashtag = searchForm.bindFromRequest.get

    Redirect(routes.Application.getTweets(hashtag.hashtag))
  }

  def getPersons = Action {
    val persons = DB.query[Person].fetch()
    Ok(Json.toJson(persons))
  }

  def getTweets(hashtag: String) = Action {

    val userList = TwitterService.getTweets(hashtag)

    val tweets: JsValue = Json.obj("tweets" -> userList)

    Ok(views.html.messages(tweets))

//    Ok(tweets)
  }

}