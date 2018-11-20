package it.maior.icaroxt.entity

//case class Shift(weekDay: WeekDay, duration:Int, crewMember:CrewMember) {
//  require(duration>0)
//
//}
case class Shift(crewMember:CrewMember,weekDay: WeekDay, duration:Int) {
  require(duration>0)

}