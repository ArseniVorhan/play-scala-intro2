package controllers


import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import services.TwitterService


class Application extends Controller{
  def index = Action {
    Ok(views.html.index("Hello World!"))
  }

  val searchForm: Form[Hashtag] = Form {
    mapping(
      "hashtag" -> text
    )(Hashtag.apply)(Hashtag.unapply)
  }

  def SearchByHashtag() = Action { implicit request =>
    val hashtag = searchForm.bindFromRequest.get

    Redirect(routes.Application.getTweets(hashtag.hashtag))
  }

  def getTweets(hashtag: String) = Action {

    val values = TwitterService.getTweets(hashtag)

//    val tweets: JsValue = Json.obj("tweets" -> userList)

    Ok(views.html.messages(values))

//    Ok(tweets)
  }

}