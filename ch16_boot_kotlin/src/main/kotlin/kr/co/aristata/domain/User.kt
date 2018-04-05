package kr.co.aristata.domain

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user")
data class User(

  @Id
  val email: String = "",

  var name: String = "",

  @Column(name = "create_date")
  val createDate: LocalDateTime =  LocalDateTime.now()

)