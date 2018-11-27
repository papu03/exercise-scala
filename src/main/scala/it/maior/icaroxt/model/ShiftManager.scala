package it.maior.icaroxt.model

import org.springframework.stereotype.Service
import it.maior.icaroxt.entity._
import it.maior.icaroxt.repository.{CrewMemberRepository, ShiftRepository}
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.mutable.ArrayBuffer

@Service
class ShiftManager(@Autowired val shiftRepository: ShiftRepository) {


  def getShiftList():List[Shift]={
    shiftRepository.shifts
  }

  def setShiftList(shifts:List[Shift])={
    shiftRepository.shifts=shifts
  }

  def getShift(id:Int):Shift={
    shiftRepository.shifts.collectFirst { case shift if shift.shiftId==id => shift }.get

  }

  def getShiftsFromCrewMemberId(crewMemberId:Int):List[Shift]={
    val shifts = shiftRepository.shifts
    shifts.filter(s => s.crewMember.id == crewMemberId)
  }

  def addShift(shift: Shift) = {

    setShiftList(shift :: shiftRepository.shifts)

  }

  def updateShift(shiftToUpdateId:Int,newShift:Shift) ={

    val shiftUpdated= newShift :: shiftRepository.shifts.filter(s => s.shiftId!=shiftToUpdateId)
    setShiftList(shiftUpdated)

  }

}
