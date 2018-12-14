package it.maior.icaroxt.shift.shiftValidations

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

  case object DurationLessThanOneDayError extends ValidationError {
    def errorMessage: String = "Duration Less Then One Day"
  }

  case object DurationGreaterThan3 extends ValidationError {
    def errorMessage: String = "Shift duration must be <= 3 days"
  }

  case object MultipleWeekShift extends ValidationError {
    def errorMessage: String = "Shift must start and terminate in the same week"
  }



}
