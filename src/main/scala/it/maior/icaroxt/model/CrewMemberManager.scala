package it.maior.icaroxt.model

import it.maior.icaroxt.entity.CrewMember
import it.maior.icaroxt.repository.CrewMemberRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

@Service
class CrewMemberManager(@Autowired val crewMemberRepository: CrewMemberRepository){

  def getCrewMembers():List[CrewMember]={
    crewMemberRepository.crewMembers
  }

  def getCrewMember(id:Int):CrewMember={
    crewMemberRepository.crewMembers.filter(u=>u.id==id)(0)
  }
}
