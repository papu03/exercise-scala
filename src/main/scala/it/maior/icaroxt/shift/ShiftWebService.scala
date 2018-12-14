package it.maior.icaroxt.shift

import cats.data.Validated._
import it.maior.icaroxt.shift.shiftValidations.ShiftValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation._

@RestController
class ShiftWebService(@Autowired val shiftManager: ShiftManager,  @Autowired val shiftValidator: ShiftValidator) {

  @GetMapping(path = Array("/shifts"))
  def getShifts() = {
    shiftManager.getShifts()
  }

  @GetMapping(path = Array("/shiftsDTO"))
  def getShiftsDTO() = {
    shiftManager.getShiftsDTO()
  }

  @GetMapping(path = Array("/shifts/{id}"))
  def getShift(@PathVariable id: Int): Shift = {
    shiftManager.getShift(id)
  }


  @GetMapping(path = Array("/shiftsDTO/{id}"))
  def getShiftDTO(@PathVariable id: Int): ShiftDTO = {
    shiftManager.getShiftDTO(id)
  }

  @GetMapping(path = Array("/crewMemberShifts/{crewMemberId}"))
  def getShiftsFromCrewMemberId(@PathVariable crewMemberId: Int) = {
    shiftManager.getShiftsFromCrewMemberId(crewMemberId)
  }

  @GetMapping(path = Array("/invalidShifts"))
  def getValidShifts()= {
    shiftManager.getInvalidShifts()
  }

  @PostMapping(path = Array("/shift"))
  def addShift(@RequestBody shift: ShiftDTO):ResponseEntity[List[String]] = {

    shiftValidator.validateAndMapToNewShift(shift.crewMemberId,shift.weekDay,shift.duration) match{

          case Valid(shift) =>  {

            shiftManager.addShift(shift)
            return ResponseEntity.ok().body(List[String]())
          }

          case Invalid(chain) => {
            val errors: List[String] = chain.foldLeft(List[String]())((list,error) => list :+ error.errorMessage)
            return ResponseEntity.badRequest().body(errors)
          }
    }
  }


  @PutMapping(Array("/shift/{shiftToUpdateId}"))
  def updateShift(@RequestBody newShift: ShiftDTO,@PathVariable shiftToUpdateId: Int):ResponseEntity[List[String]] = {

    shiftValidator.validateAndMapToExistingShift(shiftToUpdateId,newShift.crewMemberId,newShift.weekDay,newShift.duration) match{

      case Valid(shift) =>  {

        shiftManager.updateShift(shiftToUpdateId,shift)
        return ResponseEntity.ok().body(List[String]())
      }

      case Invalid(chain) => {
        val errors: List[String] = chain.foldLeft(List[String]())((list,error) => list :+ error.errorMessage)
        return ResponseEntity.badRequest().body(errors)
      }
    }

  }

  @DeleteMapping(Array("/shift/{id}"))
  def deleteShift(@PathVariable id: Int): Unit = {
    shiftManager.setShifts(shiftManager.getShifts().filter(s => s.shiftId!=id))
  }

  @DeleteMapping(Array("/crewMemberShifts/{id}"))
  def deleteShiftsMember(@PathVariable id: Int): Unit = {
    shiftManager.setShifts(shiftManager.getShifts().filter(s => s.crewMember.id!=id))
  }




//  @PostMapping(path = Array("/shift"))
//  def addShift(@RequestBody shift: ShiftDTO):ResponseEntity[String] = {
//
//    val crewMember = crewMemberManager.getCrewMembers().collectFirst { case member if member.id == shift.crewMemberId => member }
//    val weekDay = weekDayRepository.week.collectFirst { case weekDay if weekDay.name == shift.weekDay => weekDay }
//    val shifts = shiftManager.getShiftList()
//
//    (crewMember, weekDay) match{
//      case (None, _)  =>  return ResponseEntity.badRequest().body("crewMember not found")
//      case (_, None)  =>  return ResponseEntity.badRequest().body("weekDay not found")
//      case (Some(_), Some(_)) => {
//        shiftManager.addShift(new Shift(shifts.length+1,crewMember.get,weekDay.get,shift.duration))
//
//        return ResponseEntity.ok().body("{}")
//      }
//    }


//    @DeleteMapping(Array("/students/{id}"))
//    def deleteShift(@RequestBody shift: ShiftDTO): Unit = {
//      val crewMember = crewMemberManager.getCrewMembers().collectFirst { case member if member.id == shift.crewMemberId => member }
//      val weekDay = weekDayRepository.week.collectFirst { case weekDay if weekDay.name == shift.weekDay => weekDay }
//
//      (crewMember, weekDay) match {
//        case (None, _) => return ResponseEntity.badRequest().body("crewMember not found")
//        case (_, None) => return ResponseEntity.badRequest().body("weekDay not found")
//        case (Some(_), Some(_)) => {
//          val shiftToDelete = new Shift(crewMember.get, weekDay.get, shift.duration)
//        }
//      }
//    }






}
