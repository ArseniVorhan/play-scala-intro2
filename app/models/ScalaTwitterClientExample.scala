package models

/**
  * Created by Arseni on 3/3/2016.
  */

import java.util

import twitter4j.{DirectMessage, TwitterFactory}
import twitter4j.conf.ConfigurationBuilder

object ScalaTwitterClientExample {

  val messageList = new util.ArrayList[Message]()

  def getTweets: util.ArrayList[Message] = {

    // (1) config work to create a twitter object
    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey("Dwd8ys7RoHlKW3fbUPUTemJlX")
      .setOAuthConsumerSecret("um3KKZ1DI5qt042ZzMgKQw35SbEit2KIWFsDQXdQqFqHJahd0v")
      .setOAuthAccessToken("3131645877-9zgUGIbObO6iDxS8vs80s7Co0hJMBuztQBk4zCK")
      .setOAuthAccessTokenSecret("xDWqVJDRk1WFsXDGYoB7zvjr3j9O7LcX2vrq7K7hS6ODQ")
    val tf = new TwitterFactory(cb.build())
    val twitter = tf.getInstance()


    val messages =   twitter.directMessages.getDirectMessages


        val it = messages.iterator()
        while (it.hasNext) {
          val message = it.next()
          processMessage(message)
        }

    def processMessage(message: DirectMessage) = {
      messageList.add(
        new Message(message.getText, message.getRecipientScreenName))
    }

    messageList

    //    val it = statuses.iterator()
    //    while (it.hasNext()) {
    //      val status = it.next()
    //      println(status.getUser().getName() + ":" +
    //        status.getText());
    //    }

  }
}