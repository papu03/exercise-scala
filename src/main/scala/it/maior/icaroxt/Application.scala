package it.maior.icaroxt

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration}

@SpringBootApplication
class Application{}

object Application extends App {
  SpringApplication.run(classOf[Application]);
}

//From scala to JSON
@Configuration
class JacksonConfig{

  @Bean
  def scalaTypesModule()={
    DefaultScalaModule
  }
}