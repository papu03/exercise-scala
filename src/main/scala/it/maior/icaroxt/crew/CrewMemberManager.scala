package it.maior.icaroxt.crew

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CrewMemberManager(@Autowired val crewMemberRepository: CrewMemberRepository){

  def getCrewMembers():List[CrewMember]={
    crewMemberRepository.crewMembers
  }

  def getCrewMember(id:Int):CrewMember={
    crewMemberRepository.crewMembers.filter(u=>u.id==id)(0)
  }
}
