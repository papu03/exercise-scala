package it.maior.icaroxt.model

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ShiftIdGenerator() {

  var id:Int = 0

  def generateIds():Int = this.synchronized{
    id +=1
    id
  }
}
