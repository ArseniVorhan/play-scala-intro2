package models

/**
  * Created by Arseni on 3/4/2016.
  */

class Message(var text: String, var recipient: String){
  override def toString: String = "Recipient: " + recipient + "\n Message:" + text +"\n\n"
}
