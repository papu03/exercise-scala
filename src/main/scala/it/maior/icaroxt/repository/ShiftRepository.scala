package it.maior.icaroxt.repository

import it.maior.icaroxt.entity.Shift
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import scala.collection.mutable.ArrayBuffer

@Repository
class ShiftRepository(@Autowired val crewMemberManager: CrewMemberRepository, @Autowired val weekDayManager: WeekDayRepository) {

  val shifts:ArrayBuffer[Shift] = ArrayBuffer(
    createShiftFromMemberName("Riccardo","Monday",3),
    createShiftFromMemberName("Tommaso","Wednesday",1),
    createShiftFromMemberName("Giuseppe","Monday",4),
    createShiftFromMemberName("Marco","Saturday",2),
    createShiftFromMemberName("Marco","Sunday",6)
  )


  def createShiftFromMemberName(name:String,weekDayName:String,duration:Int)={

    val weekDay = weekDayManager.week.filter(weekDay=>weekDay.name==weekDayName)(0)
    new Shift(crewMemberManager.crewMembers.filter(member=>member.name==name)(0),weekDay,duration)
  }
}
