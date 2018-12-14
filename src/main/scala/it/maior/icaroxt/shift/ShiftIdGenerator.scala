package it.maior.icaroxt.shift

import org.springframework.stereotype.Component

@Component
class ShiftIdGenerator() {

  var id:Int = 0

  def generateIds():Int = this.synchronized{
    id +=1
    id
  }
}
