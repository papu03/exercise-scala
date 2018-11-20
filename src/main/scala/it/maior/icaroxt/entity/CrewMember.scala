package it.maior.icaroxt.entity

case class CrewMember(id:Int,name:String) {
  require(id>0)
}
