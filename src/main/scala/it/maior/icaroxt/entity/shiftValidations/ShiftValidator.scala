package it.maior.icaroxt.entity.shiftValidations

import cats.data.Validated._
import cats.data.ValidatedNec
import cats.implicits._
import it.maior.icaroxt.entity.{CrewMember, Shift, WeekDay}
import it.maior.icaroxt.entity.shiftValidations.errors.{CrewMemberDoesNotExistError, DurationLessThenOneDayError, ValidationError, WeekdayDoesNotExistError}
import it.maior.icaroxt.model.{CrewMemberManager, ShiftIdGenerator}
import it.maior.icaroxt.repository.WeekDayRepository
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

    if(duration>0) duration.validNec else DurationLessThenOneDayError.invalidNec
  }



  def validateAndMapToShift(crewMemberId: Int, weekdayName: String, duration: Int): ValidationResult[Shift] = {
    (validateCrewMember(crewMemberId),
      validateWeekday(weekdayName),
      validateDuration(duration)
    ).mapN(Shift(shiftIdGenerator.generateIds(), _, _, _))
  }




}


