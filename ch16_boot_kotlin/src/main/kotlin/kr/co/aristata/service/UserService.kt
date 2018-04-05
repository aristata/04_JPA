package kr.co.aristata.service

import kr.co.aristata.domain.User
import kr.co.aristata.exception.DuplicateEmailException
import kr.co.aristata.exception.UserNotFoundException
import kr.co.aristata.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  @Autowired
  val userRepository: UserRepository
) {

  // insert
  @Transactional
  fun joinUser(user: User) {
    if (userRepository.find(user.email) != null) {
      throw DuplicateEmailException()
    } else {
      userRepository.save(user)
    }
  }

  // find
  fun findUser(email: String): User {
    return userRepository.find(email)
  }

  // find all
  fun findAll(): List<User> {
    return userRepository.findAll()
  }

  // update
  @Transactional
  fun updateUser(email: String, newName: String) {
    var user: User = userRepository.find(email) ?: throw UserNotFoundException()
    user.name = newName
    userRepository.save(user)
  }

  // delete
  @Transactional
  fun deleteUser(email: String) {
    val user: User = userRepository.find(email) ?: throw UserNotFoundException()
    userRepository.remove(user)
  }
}