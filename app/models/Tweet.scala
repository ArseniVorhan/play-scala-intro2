package models
import play.api.libs.json._

case class Tweet(username: String, tweet: String, sentiment: String, score: String)

object Tweet {

  implicit object TweetFormat extends Format[Tweet] {

    // convert from Tweet object to JSON (serializing to JSON)
    def writes(tweet: Tweet): JsValue = {
      //  tweetSeq == Seq[(String, play.api.libs.json.JsString)]
      val tweetSeq = Seq(
        "username" -> JsString(tweet.username),
        "tweet" -> JsString(tweet.tweet),
        "sentiment" -> JsString(tweet.sentiment),
        "score" -> JsString(tweet.score)
      )
      JsObject(tweetSeq)
    }

    // convert from JSON string to a Tweet object (de-serializing from JSON)
    // (i don't need this method; just here to satisfy the api)
    def reads(json: JsValue): JsResult[Tweet] = {
      JsSuccess(Tweet("", "", "", ""))
    }

  }

}