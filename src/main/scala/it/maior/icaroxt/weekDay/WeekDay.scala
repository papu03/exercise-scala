package it.maior.icaroxt.weekDay

sealed trait WeekDay{
  val daysToNextWeek:Int
  val name:String
}

case object Monday extends WeekDay {
  val daysToNextWeek = 8
  val name="Monday"
}
case object Tuesday extends WeekDay {
  val daysToNextWeek = 7
  val name="Tuesday"
}
case object Wednesday extends WeekDay {
  val daysToNextWeek = 6
  val name="Wednesday"
}
case object Thursday extends WeekDay {
  val daysToNextWeek = 5
  val name="Thursday"
}
case object Friday extends WeekDay {
  val daysToNextWeek = 4
  val name="Friday"
}
case object Saturday extends WeekDay {
  val daysToNextWeek = 3
  val name="Saturday"
}
case object Sunday extends WeekDay {
  val daysToNextWeek = 2
  val name="Sunday"
}


