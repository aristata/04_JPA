package kr.co.aristata.controller

import kr.co.aristata.domain.User
import kr.co.aristata.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
  @Autowired
  private val userService: UserService
) {

  @GetMapping(path = ["/users"])
  fun users(): List<User> {
    return userService.findAll()
  }

  @GetMapping(path = ["/users/{email}"])
  fun user(@PathVariable email: String): User {
    return userService.findUser(email)
  }

  @PostMapping(path = ["/users"])
  fun joinUser(user: User): String {
    userService.joinUser(user)
    return "${user.email} 의 계정을 생성하였습니다."
  }

  @PutMapping(path = ["/users/{email}"])
  fun updateUser(@PathVariable email: String, newName: String): String {
    userService.updateUser(email, newName)
    return "$email 의 name을 $newName 으로 변경하였습니다."
  }

  @DeleteMapping(path = ["/users/{email}"])
  fun deleteUser(@PathVariable email: String): String {
    userService.deleteUser(email)
    return "$email 의 계정을 삭제하였습니다."
  }
}