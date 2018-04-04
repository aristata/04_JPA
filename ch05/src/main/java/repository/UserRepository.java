package repository;

import configuration.CustomEntityManagerFactory;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserRepository {

  public User find(String email) {
    EntityManager entityManager = CustomEntityManagerFactory.currentEntityManager();
    return entityManager.find(User.class, email);
  }

  public void save(User user) {
    EntityManager entityManager = CustomEntityManagerFactory.currentEntityManager();
    entityManager.persist(user);
  }

  public void remove(User user) {
    EntityManager entityManager = CustomEntityManagerFactory.currentEntityManager();
    entityManager.remove(user);
  }

  public List<User> findAll() {
    TypedQuery<User> query =
      CustomEntityManagerFactory
        .currentEntityManager()
        .createQuery("select u from User u order by u.name", User.class);
    return query.getResultList();
  }
}
