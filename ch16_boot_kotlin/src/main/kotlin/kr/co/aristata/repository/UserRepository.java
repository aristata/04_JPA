package kr.co.aristata.repository;

import kr.co.aristata.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public User find(String email) {
    return entityManager.find(User.class, email);
  }

  public void save(User user) {
    entityManager.persist(user);
  }

  public void remove(User user) {
    entityManager.remove(user);
  }

  public List<User> findAll() {
    TypedQuery<User> query = entityManager.createQuery(
      "select u from User u order by u.name",
      User.class
    );
    return query.getResultList();
  }

}