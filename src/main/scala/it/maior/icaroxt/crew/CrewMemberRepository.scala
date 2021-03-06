package it.maior.icaroxt.crew

import org.springframework.stereotype.Repository

@Repository
class CrewMemberRepository {

  val crewMembers:List[CrewMember] = createMemberFromNames(List("Riccardo","Marco","Giuseppe","Tommaso","Vittorio","Vishal"))

  private def createMemberFromNames(membersName:List[String])={
    var id_count=1
    var crewMemberList:List[CrewMember] = List()

    for(memberName <- membersName) {
      crewMemberList = new CrewMember(id = id_count, name = memberName) :: crewMemberList
      id_count += 1
    }
    crewMemberList
  }

}
