package jpastart.jpa;

import com.aristatait.model.User;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;


public class JpaTest {

  @Test
  public void createEntityManagerFactory() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpastart");

    EntityManager entityManager = emf.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      entityManager.persist(new User("user2@user.com", "user", new Date()));
      transaction.commit();
    } catch (Exception ex) {
      entityManager.getTransaction().rollback();
    } finally {
      entityManager.close();
    }
    emf.close();
  }
}
