package it.maior.icaroxt.repository

import it.maior.icaroxt.entity.Shift
import it.maior.icaroxt.model.ShiftIdGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import scala.collection.mutable.ArrayBuffer

@Repository
class ShiftRepository(@Autowired val crewMemberManager: CrewMemberRepository, @Autowired val weekDayManager: WeekDayRepository, @Autowired val shiftIdGenerator:ShiftIdGenerator) {

  var shifts:ArrayBuffer[Shift] = ArrayBuffer(
    createShiftFromMemberName("Riccardo","Monday",3),
    createShiftFromMemberName("Tommaso","Wednesday",1),
    createShiftFromMemberName("Giuseppe","Monday",4),
    createShiftFromMemberName("Marco","Saturday",2),
    createShiftFromMemberName("Marco","Sunday",6)
  )


  def createShiftFromMemberName(name:String,weekDayName:String,duration:Int)={


    val weekDay = weekDayManager.week.collectFirst{case weekDay if weekDay.name==weekDayName => weekDay}
    val member = crewMemberManager.crewMembers.collectFirst{case member if member.name==name => member}

    val shiftId = shiftIdGenerator.generateIds()

    new Shift(shiftId,member.get,weekDay.get,duration)
  }
}
