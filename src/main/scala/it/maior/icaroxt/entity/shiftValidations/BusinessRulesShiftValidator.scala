package it.maior.icaroxt.entity.shiftValidations

import cats.data.ValidatedNec
import it.maior.icaroxt.entity.{CrewMember, Shift}
import it.maior.icaroxt.entity.shiftValidations.errors.{DurationGreaterThan3, MultipleWeekShift, ValidationError}
import cats.implicits._
import org.springframework.stereotype.Component

@Component
class BusinessRulesShiftValidator {

  type ValidationResult[A] = ValidatedNec[ValidationError, A]
//type ValidationResult[A] = ValidatedNec[A,ValidationError]

  private def checkDurationLessThan4(duration: Int): ValidationResult[Int] = {

    if(duration<=3) duration.validNec else DurationGreaterThan3.invalidNec
  }

  private def checkShiftIsWithinSingleWeek(duration: Int,daysToNextWeek:Int): ValidationResult[Int] = {

    if(duration<daysToNextWeek) duration.validNec else MultipleWeekShift.invalidNec
  }
//  private def checkDurationGreaterThan3(duration: Int)= {
//
//    if(duration<=0) DurationGreaterThan3.validNec else duration.invalidNec
//  }
//
//  private def checkMultipleWeekShift(duration: Int,daysToNextWeek:Int)= {
//
//    if(duration>=daysToNextWeek) MultipleWeekShift.validNec else duration.invalidNec
//  }

//  def validateAndMapToShift(duration: Int, daysToNextWeek: Int) = {
//    (checkDurationGreaterThan3(duration),
//      checkMultipleWeekShift(duration,daysToNextWeek)
//    ).mapN(_,_)
//
//  }

  def validateAndMapToShift(shift: Shift) = {
    (checkDurationLessThan4(shift.duration),
      checkShiftIsWithinSingleWeek(shift.duration,shift.weekDay.daysToNextWeek)
    ).mapN((_,_) => List())

  }

}
