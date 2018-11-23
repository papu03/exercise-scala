package it.maior.icaroxt.webservice

import it.maior.icaroxt.entity.shiftValidations.ShiftValidator
import it.maior.icaroxt.entity.{CrewMember, Shift}
import it.maior.icaroxt.repository.{CrewMemberRepository, WeekDayRepository}
import it.maior.icaroxt.model.{CrewMemberManager, ShiftManager}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RestController}
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import cats.data.Validated._
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

@RestController
class ShiftWebService(@Autowired val shiftManager: ShiftManager,  @Autowired val shiftValidator: ShiftValidator) {

  @GetMapping(path = Array("/shifts"))
  def getShiftList() = {
    shiftManager.getShiftList()
  }

  @GetMapping(path = Array("/shifts/{id}"))
  def getShift(@PathVariable id: Int): Shift = {
    shiftManager.getShift(id)
  }

  @PostMapping(path = Array("/shift"))
  def addShift(@RequestBody shift: ShiftDTO):ResponseEntity[List[String]] = {

    val shifts = shiftManager.getShiftList()

    shiftValidator.validateAndMapToShift(shift.crewMemberId,shift.weekDay,shift.duration) match{

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



  @DeleteMapping(Array("/shift/{id}"))
  def deleteStudent(@PathVariable id: Int): Unit = {
    shiftManager.setShiftList(shiftManager.getShiftList().filter(s => s.shiftId!=id))
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
