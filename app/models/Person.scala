package models

import play.api.libs.json.Json

/**
  * Created by arseni.vorhan on 02.03.2016.
  */
case class Person(name: String)

object Person {

  implicit val personFormat = Json.format[Person]
}
