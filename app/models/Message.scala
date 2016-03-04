package models

import play.api.libs.json.Json

/**
  * Created by Arseni on 3/4/2016.
  */

class Message(var text: String, var recipient: String){

implicit val messageFormat = Json.format[Message]

}