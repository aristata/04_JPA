package service;

import configuration.CustomEntityManagerFactory;
import exception.DuplicateEmailException;
import model.User;
import repository.UserRepository;

import javax.persistence.EntityManager;

public class JoinService {

  private UserRepository userRepository = new UserRepository();

  public void join(User user) {

    EntityManager entityManager = CustomEntityManagerFactory.currentEntityManager();

    try {
      entityManager.getTransaction().begin();

      User user1 = userRepository.find(user.getEmail());
      if (user1 != null) {
        throw new DuplicateEmailException();
      }
      userRepository.save(user);
      entityManager.getTransaction().commit();
    } catch (Exception ex) {
      entityManager.getTransaction().rollback();
    } finally {
      CustomEntityManagerFactory.closeCurrentEntityManager();
    }
  }
}
