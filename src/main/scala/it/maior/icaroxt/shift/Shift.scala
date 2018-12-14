package it.maior.icaroxt.shift

import it.maior.icaroxt.crew.CrewMember
import it.maior.icaroxt.weekDay.WeekDay

//case class Shift(weekDay: WeekDay, duration:Int, crewMember:CrewMember) {
//  require(duration>0)
//
//}
case class Shift( shiftId:Int,crewMember:CrewMember,weekDay: WeekDay, duration:Int) {
  require(duration>0)

}
