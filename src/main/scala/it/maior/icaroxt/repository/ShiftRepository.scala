package it.maior.icaroxt.repository

import it.maior.icaroxt.entity.Shift
import it.maior.icaroxt.model.ShiftIdGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import scala.collection.mutable.ArrayBuffer

@Repository
class ShiftRepository(@Autowired val crewMemberManager: CrewMemberRepository, @Autowired val weekDayManager: WeekDayRepository, @Autowired val shiftIdGenerator:ShiftIdGenerator) {

  var shifts:List[Shift] = List(
    createShiftFromInfo("Riccardo","Monday",3),
    createShiftFromInfo("Tommaso","Wednesday",1),
    createShiftFromInfo("Giuseppe","Monday",4),
    createShiftFromInfo("Marco","Saturday",2),
    createShiftFromInfo("Marco","Sunday",6),
    createShiftFromInfo("Vittorio","Wednesday",1)
  )


  def createShiftFromInfo(name:String, weekDayName:String, duration:Int)={


    val weekDay = weekDayManager.week.collectFirst{case weekDay if weekDay.name==weekDayName => weekDay}
    val member = crewMemberManager.crewMembers.collectFirst{case member if member.name==name => member}

    val shiftId = shiftIdGenerator.generateIds()

    new Shift(shiftId,member.get,weekDay.get,duration)
  }
}
