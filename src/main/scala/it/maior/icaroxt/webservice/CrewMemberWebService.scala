package it.maior.icaroxt.webservice

import it.maior.icaroxt.entity.{CrewMember, Shift}
import it.maior.icaroxt.model.{CrewMemberManager, ShiftManager}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._


@RestController
class CrewMemberWebService(@Autowired val crewMemberManager: CrewMemberManager){

  @GetMapping(path = Array("/members"))
  def getCrewMemberList(): List[CrewMember] = {
    crewMemberManager.getCrewMembers()
  }

  @GetMapping(path = Array("/members/{id}"))
  def getCrewMember(@PathVariable id:Int): CrewMember = {
    crewMemberManager.getCrewMember(id)
  }

}