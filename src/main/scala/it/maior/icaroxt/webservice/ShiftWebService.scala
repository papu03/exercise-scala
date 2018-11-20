package it.maior.icaroxt.webservice

import it.maior.icaroxt.entity.{CrewMember, Shift}
import it.maior.icaroxt.repository.{CrewMemberRepository, WeekDayRepository}
import it.maior.icaroxt.model.{CrewMemberManager, ShiftManager}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpHeaders, HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{GetMapping, PostMapping, RequestBody, RestController}
import org.springframework.http.ResponseEntity
@RestController
class ShiftWebService(@Autowired val shiftManager: ShiftManager, @Autowired val crewMemberManager: CrewMemberManager, @Autowired val weekDayRepository: WeekDayRepository) {

  @GetMapping(path = Array("/shifts"))
  def getShiftList() = {
    shiftManager.getShiftList()
  }

  @PostMapping(path = Array("/shift"))
  def addShift(@RequestBody shift: ShiftDTO):ResponseEntity[String] = {

    val crewMember = crewMemberManager.getCrewMembers().collectFirst { case member if member.id == shift.crewMemberId => member }
    val weekDay = weekDayRepository.week.collectFirst { case weekDay if weekDay.name == shift.weekDay => weekDay }

    (crewMember, weekDay) match{
      case (None, _)  =>  return ResponseEntity.badRequest().body("crewMember not found")
      case (_, None)  =>  return ResponseEntity.badRequest().body("weekDay not found")
      case (Some(_), Some(_)) => {
        shiftManager.addShift(new Shift(crewMember.get,weekDay.get,shift.duration))

        return ResponseEntity.ok().body("{}")
      }
    }



  }

}
