package it.maior.icaroxt.entity.shiftValidations

package object errors {

  sealed trait ValidationError {
    def errorMessage: String
  }

  case object CrewMemberDoesNotExistError extends ValidationError {
    def errorMessage: String = "CrewMember Does Not Exist"
  }

  case object WeekdayDoesNotExistError extends ValidationError {
    def errorMessage: String = "Weekday Does Not Exist."
  }

  case object DurationLessThenOneDayError extends ValidationError {
    def errorMessage: String = "Duration Less Then One Day"
  }



}
