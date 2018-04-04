package service;

import configuration.CustomEntityManagerFactory;
import jpastart.reserve.application.UserNotFoundException;
import model.User;
import repository.UserRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class WithdrawService {

  private UserRepository userRepository;

  public void withdraw(String email) {

    EntityManager entityManager = CustomEntityManagerFactory.currentEntityManager();
    EntityTransaction entityTransaction = entityManager.getTransaction();

    try {
      entityTransaction.begin();

      User user = userRepository.find(email);
      if (user == null) {
        throw new UserNotFoundException();
      }
      userRepository.remove(user);

      entityTransaction.commit();
    } catch (Exception ex) {
      entityTransaction.rollback();
    } finally {
      CustomEntityManagerFactory.closeCurrentEntityManager();
    }

  }

  @Inject
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
