package services

/**
  * Created by Arseni on 3/3/2016.
  */

import models.Tweet
import play.api.libs.json.Json
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Status, TwitterFactory}

import scala.collection.mutable.ListBuffer
import scalaj.http.Http

object TwitterService {


  def getTweets(hashtag: String): Array[Integer] = {
    var messageList = new ListBuffer[Tweet]()

    // (1) config work to create a twitter object
    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey("Dwd8ys7RoHlKW3fbUPUTemJlX")
      .setOAuthConsumerSecret("um3KKZ1DI5qt042ZzMgKQw35SbEit2KIWFsDQXdQqFqHJahd0v")
      .setOAuthAccessToken("3131645877-9zgUGIbObO6iDxS8vs80s7Co0hJMBuztQBk4zCK")
      .setOAuthAccessTokenSecret("xDWqVJDRk1WFsXDGYoB7zvjr3j9O7LcX2vrq7K7hS6ODQ")
    val tf = new TwitterFactory(cb.build())
    val twitter = tf.getInstance()


    val query = new Query("#" + hashtag)
    query.setCount(40)

    val qr = twitter.search(query)
    val qrTweets = qr.getTweets()



//    val messages = twitter.timelines.getUserTimeline


    val it = qrTweets.iterator()
    while (it.hasNext) {
      val message = it.next()
      processMessage(message)
    }

    def processMessage(status: Status) = {

      val result = Http("https://api.havenondemand.com/1/api/sync/analyzesentiment/v1")
        .param("text", status.getText)
        .param("apikey", "f0f2e750-ce78-48e0-a4cb-a434632eb91c")
        .asString

      val SAResponse = Json.parse(result.body)
      val sentiment = SAResponse.\\("aggregate")(0).\\("sentiment")(0).toString().replaceAll("\"", "")
      val score = SAResponse.\\("aggregate")(0).\\("score")(0).toString().replaceAll("\"", "")
      messageList += new Tweet(status.getUser.getName, status.getText, sentiment, score)

    }


    var positiveNumbers = 0;
    var negativeNumbers = 0;
    var neutralNumbers = 0;

    val tweetIterator = messageList.iterator;
    while (tweetIterator.hasNext) {
      val tweet = tweetIterator.next()
      if (tweet.sentiment == "positive"){
        positiveNumbers+=1
      }else if (tweet.sentiment == "negative"){
        negativeNumbers+=1
      }else{
        neutralNumbers+=1
      }
    }

    val chars = new Array[Integer](4)
    chars(0) = positiveNumbers;
    chars(1) = negativeNumbers;
    chars(2) = neutralNumbers;
    chars(3) = messageList.size;



    chars
  }
}