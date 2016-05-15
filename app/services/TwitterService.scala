package services

/**
  * Created by Arseni on 3/3/2016.
  */

import models.Tweet
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Query, Status, TwitterFactory}

import scala.collection.mutable.ListBuffer

object TwitterService {


  var messageList = new ListBuffer[Tweet]()

  def getTweets(hashtag: String): List[Tweet] = {

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
    query.setCount(100)

    val qr = twitter.search(query)
    val qrTweets = qr.getTweets()



//    val messages = twitter.timelines.getUserTimeline


    val it = qrTweets.iterator()
    while (it.hasNext) {
      val message = it.next()
      processMessage(message)
    }

    def processMessage(status: Status) = {
      messageList += new Tweet(status.getText, status.getCreatedAt.toString)

    }

    messageList.toList

  }
}