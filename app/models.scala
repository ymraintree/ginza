package models

import play.db.jpa._
import javax.persistence._

import java.util.Date

@Entity
class Event (
  var title:String,
  var location:String,
  var date:Date,
  var fromTime:String,
  var toTime:String
) extends Model {
  def dateFormatted = if (date == null) "" else Event.sdf.format(date)
  def getTime = fromTime + "から" + toTime + "まで"
  def getMarkerText = "%s（%s） %s".format(title, location, getTime)
}

object Event extends QueryOn[Event] {
  val sdf = new java.text.SimpleDateFormat("yyyy-MM-dd")
}
