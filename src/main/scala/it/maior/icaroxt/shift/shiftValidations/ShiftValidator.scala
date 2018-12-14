package it.maior.icaroxt.shift.shiftValidations

import cats.data.Validated._
import cats.data.ValidatedNec
import cats.implicits._
import it.maior.icaroxt.crew.{CrewMember, CrewMemberManager}
import it.maior.icaroxt.shift.shiftValidations.errors.{CrewMemberDoesNotExistError, DurationLessThanOneDayError, ValidationError, WeekdayDoesNotExistError}
import it.maior.icaroxt.shift.{Shift, ShiftIdGenerator}
import it.maior.icaroxt.weekDay.{WeekDay, WeekDayRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ShiftValidator(@Autowired val crewMemberManager: CrewMemberManager,
                     @Autowired val weekDayRepository: WeekDayRepository,
                     @Autowired val shiftIdGenerator:ShiftIdGenerator) {

  type ValidationResult[A] = ValidatedNec[ValidationError, A]

  private def validateCrewMember(crewMemberId: Int): ValidationResult[CrewMember] = {

    val crewMemberOption:Option[CrewMember] = crewMemberManager.getCrewMembers().collectFirst { case member if member.id == crewMemberId => member }

    crewMemberOption match {

      case Some(crewMember) => crewMember.validNec
      case None => CrewMemberDoesNotExistError.invalidNec
    }
  }

  private def validateWeekday(weekdayName: String): ValidationResult[WeekDay] = {

    val weekdayOption:Option[WeekDay] = weekDayRepository.week.collectFirst { case weekDay if weekDay.name == weekdayName => weekDay }

    weekdayOption match {

      case Some(weekDay) => weekDay.validNec
      case None => WeekdayDoesNotExistError.invalidNec
    }
  }

  private def validateDuration(duration: Int): ValidationResult[Int] = {

    if(duration>0) duration.validNec else DurationLessThanOneDayError.invalidNec
  }



  def validateAndMapToNewShift(crewMemberId: Int, weekdayName: String, duration: Int): ValidationResult[Shift] = {
    validateAndMapToShift(shiftIdGenerator.generateIds(), crewMemberId, weekdayName, duration)
  }

  def validateAndMapToExistingShift(shiftToUpdateId:Int, crewMemberId: Int, weekdayName: String, duration: Int): ValidationResult[Shift] = {
    validateAndMapToShift(shiftToUpdateId, crewMemberId, weekdayName, duration)
  }

  private def validateAndMapToShift(shiftId: Int, crewMemberId: Int, weekdayName: String, duration: Int) = {
    (validateCrewMember(crewMemberId),
      validateWeekday(weekdayName),
      validateDuration(duration)
    ).mapN(Shift(shiftId, _, _, _))
  }





}


