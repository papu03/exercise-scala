package it.maior.icaroxt.repository

import it.maior.icaroxt.entity._
import org.springframework.stereotype.Repository

@Repository
class WeekDayRepository {

  val week = List(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)

}
