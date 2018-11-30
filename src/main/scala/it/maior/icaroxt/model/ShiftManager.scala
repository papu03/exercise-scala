package it.maior.icaroxt.model

import cats.data.NonEmptyChain
import cats.data.Validated.{Invalid, Valid}
import org.springframework.stereotype.Service
import it.maior.icaroxt.entity._
import it.maior.icaroxt.repository.{CrewMemberRepository, ShiftRepository}
import it.maior.icaroxt.webservice.ShiftDTO
import org.springframework.beans.factory.annotation.Autowired
import cats.implicits._
import it.maior.icaroxt.entity.shiftValidations.{BusinessRulesShiftValidator, ShiftValidator, errors}
import org.springframework.http.ResponseEntity

import scala.collection.mutable.ArrayBuffer

@Service
class ShiftManager(@Autowired val shiftRepository: ShiftRepository, @Autowired val businessRulesShiftValidator: BusinessRulesShiftValidator) {
  //class ShiftManager(@Autowired val shiftRepository: ShiftRepository) {


  def getShifts(): List[Shift] = {
    shiftRepository.shifts
  }

  def getShiftsDTO(): List[ShiftDTO] = {

    shiftRepository.shifts.map(shift => new ShiftDTO(shift.crewMember.id, shift.weekDay.name, shift.duration, shift.shiftId))
  }

  def setShifts(shifts: List[Shift]) = {
    shiftRepository.shifts = shifts
  }

  def getShift(id: Int): Shift = {
    shiftRepository.shifts.collectFirst { case shift if shift.shiftId == id => shift }.get
  }

  def getShiftDTO(id: Int): ShiftDTO = {

    getShiftsDTO().collectFirst { case shift if shift.shiftId == id => shift }.get

  }

  def getInvalidShifts() = {

    def extractErrorMessages(chain: NonEmptyChain[errors.ValidationError]) = {
      chain.map(error => error.errorMessage).toList
    }

    shiftRepository.shifts
      .map(shift => (shift, businessRulesShiftValidator.validateAndMapToShift(shift)))
      .collect { case (shift, Invalid(chain)) => InvalidShift(shift, extractErrorMessages(chain)) }

    //
    //    shiftRepository.shifts.map(shift => {
    //      businessRulesShiftValidator.validateAndMapToShift(shift) match {
    //        case Valid(a) => Option.empty
    //        case Invalid(chain) => Some(InvalidShift(shift, extractErrorMessages(chain)))
    //      }
    //    }).filter(option => option.isDefined).map(option => option.get)

    //    shiftRepository.shifts.map(shift => {
    //      businessRulesShiftValidator.validateAndMapToShift(shift) match {
    //        case Valid(a) => None
    //        case Invalid(chain) => InvalidShift(shift, extractErrorMessages(chain))
    //      }
    //    })


  }

  def getShiftsFromCrewMemberId(crewMemberId: Int): List[Shift] = {
    val shifts = shiftRepository.shifts
    shifts.filter(s => s.crewMember.id == crewMemberId)
  }

  def addShift(shift: Shift) = {

    setShifts(shift :: shiftRepository.shifts)

  }

  def updateShift(shiftToUpdateId: Int, newShift: Shift) = {

    val shiftUpdated = newShift :: shiftRepository.shifts.filter(s => s.shiftId != shiftToUpdateId)
    setShifts(shiftUpdated)

  }

}
