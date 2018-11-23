//package it.maior.icaroxt
//
//
//import com.fasterxml.jackson.core.{JsonParser, JsonProcessingException}
//import com.fasterxml.jackson.databind.DeserializationContext
//import com.fasterxml.jackson.databind.JsonDeserializer
//import java.io.IOException
//
//import com.fasterxml.jackson.databind.node.{ObjectNode, TextNode}
//import it.maior.icaroxt.entity.{CrewMember, Monday, Shift}
//import it.maior.icaroxt.repository.{CrewMemberRepository, WeekDayRepository}
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.jackson.JsonComponent
//
//import scala.util.Try
//
//@JsonComponent
//class ShiftDeserializer(@Autowired val crewMemberManager: CrewMemberRepository, @Autowired val weekDayManager: WeekDayRepository) extends JsonDeserializer[Shift] {
//
//
//  @throws[IOException]
//  @throws[JsonProcessingException]
//  def deserialize(jp: JsonParser , ctxt: DeserializationContext): Shift = {
//
//    val treeNode:ObjectNode = jp.getCodec.readTree(jp)
//
//    val crewMemberId = treeNode.get("crewMemberId").asInt()
//    val duration = treeNode.get("duration").asInt()
//    val weekDayName = treeNode.get("weekDay").asText()
//
//    val crewMember = crewMemberManager.crewMembers.collectFirst{ case member if member.id==crewMemberId =>member}
//    val weekDay = weekDayManager.week.filter(weekDay=>weekDay.name==weekDayName)(0)
//
//    new Shift(crewMember.get,weekDay = weekDay,duration = duration)
//    //new Shift(new CrewMember(crewMemberId, ) = crewMember,weekDay = weekDay,duration = duration)
//  }
//
//}

