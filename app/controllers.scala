package controllers

import play._
import play.mvc._
import models._
import java.util.Date
import java.util.Calendar

object Application extends Controller {
    
  def index(pDate:String = "") = {
    var date = pDate match {
      case "" => Event.sdf.parse(Event.sdf.format(new java.util.Date))
      case _ => Event.sdf.parse(pDate)
    }
    val events = Event.find("byDate", date).fetch()
    val titleLine = {pDate match {
      case "" => "今日（" + Event.sdf.format(date) + "）"
      case _ => Event.sdf.format(date)
    }} + "のイベント"
    Template(
      'events -> events,
      'titleLine -> titleLine,
      'nextDate -> Event.sdf.format(getNextDate(date))
    )
  }

  private def getNextDate(date:Date) = {
    val wk = Calendar.getInstance
    wk.setTime(date)
    wk.add(Calendar.DATE, 1)
    wk.getTime
  }

  def index2(message:String = "") = {
    val events = Event.findAll
    Template(
      'events -> events,
      'message -> message
    )
  }

  def index3(message:String = "", id:String) = {
    println(params.get("id"))
    val wk = Event.sdf.parse(Event.sdf.format(new java.util.Date))
    val events = Event.find("select e from Event e where ? <= date order by title, date, fromTime", wk).fetch
    var event = new Event(null, null, null, null, null)
    if (params.get("id") != null) {
      val id = java.lang.Long.valueOf(params.get("id"))
      event = Event.findById(id).get
      println(event.date)
    }
    Template(
      'events -> events,
      'event -> event,
      'message -> message
    )
  }

  def map = {
    val events = Event.findAll
    Template(
      'events -> events
    )
  }

  def createEvent(title:String, location:String, date:String, fromTime:String, toTime:String) = {
    val event = new Event(title, location, Event.sdf.parse(date), fromTime, toTime)
    event.save
    Action(index2("%sを作成しました".format(event.title)))
  }

  def deleteEvent(id:Array[Long]) = {
    if (id == null) {
      Action(index3("削除するデータの指定がありません", null))
    } else {
      val res = id.map(i => 
	Event.findById(i) match {
	  case Some(event) =>
	    event.delete
	    event.title + "を削除しました"
	  case None =>
	    "ID: " + i + "が見つかりません"
	}
		     )
      Action(index3(res.mkString(","), null))
    }
  }

  def updateEvent(event:Event) = {
    println(params.get("event.location"))
    var mes = event.title
    if (params.get("update") == null) {
      val eventNew = new Event(event.title, event.location, event.date, event.fromTime, event.toTime)
      eventNew.save
      mes += "を新規登録しました"
    } else {
      event.save
      mes += "を更新しました"
    }
    Action(index3(mes, null))
  }

}
