package it.maior.icaroxt.model

import org.springframework.stereotype.Service
import it.maior.icaroxt.entity._
import it.maior.icaroxt.repository.{CrewMemberRepository, ShiftRepository}
import it.maior.icaroxt.webservice.ShiftDTO
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.mutable.ArrayBuffer

@Service
class ShiftManager(@Autowired val shiftRepository: ShiftRepository) {


  def getShifts():List[Shift]={
    shiftRepository.shifts
  }

  def getShiftsDTO():List[ShiftDTO]={

    shiftRepository.shifts.map(shift => new ShiftDTO(shift.crewMember.id,shift.weekDay.name,shift.duration,shift.shiftId))
  }

  def setShifts(shifts:List[Shift])={
    shiftRepository.shifts=shifts
  }

  def getShift(id:Int):Shift={
    shiftRepository.shifts.collectFirst { case shift if shift.shiftId==id => shift }.get
  }

  def getShiftDTO(id:Int):ShiftDTO={

    getShiftsDTO().collectFirst { case shift if shift.shiftId==id => shift }.get

  }

  def getShiftsFromCrewMemberId(crewMemberId:Int):List[Shift]={
    val shifts = shiftRepository.shifts
    shifts.filter(s => s.crewMember.id == crewMemberId)
  }

  def addShift(shift: Shift) = {

    setShifts(shift :: shiftRepository.shifts)

  }

  def updateShift(shiftToUpdateId:Int,newShift:Shift) ={

    val shiftUpdated= newShift :: shiftRepository.shifts.filter(s => s.shiftId!=shiftToUpdateId)
    setShifts(shiftUpdated)

  }

}
