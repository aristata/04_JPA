package jpastart.jpa;

import configuration.CustomEntityManagerFactory;
import model.Address;
import model.City;
import model.ContactInfo;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class NestedEmbeddableTest extends JpaTestBase {
  @Test
  public void insert() {
    EntityManager entityManager = CustomEntityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      City city = new City(
        "서울",
        new ContactInfo("02-120", "seoul@seoul.kr",
          new Address("04524", "서울특별시 중구 ", "세종대로 110"))
      );
      entityManager.persist(city);

      transaction.commit();
    } catch (Exception ex) {
      transaction.rollback();
      throw ex;
    } finally {
      entityManager.close();
    }
  }
}
