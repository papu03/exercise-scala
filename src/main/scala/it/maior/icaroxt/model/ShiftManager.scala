package it.maior.icaroxt.model

import org.springframework.stereotype.Service
import it.maior.icaroxt.entity._
import it.maior.icaroxt.repository.{CrewMemberRepository, ShiftRepository}
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.mutable.ArrayBuffer

@Service
class ShiftManager(@Autowired val shiftRepository: ShiftRepository) {


  def getShiftList():ArrayBuffer[Shift]={
    shiftRepository.shifts
  }

  def setShiftList(shifts:ArrayBuffer[Shift])={
    shiftRepository.shifts=shifts
  }

  def getShift(id:Int):Shift={
    shiftRepository.shifts.collectFirst { case shift if shift.shiftId==id => shift }.get

  }

  def addShift(shift: Shift) = {

    shiftRepository.shifts.append(shift)

  }

}
