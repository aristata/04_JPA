package configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomEntityManagerFactory {

  private static EntityManagerFactory entityManagerFactory;

  public static void init() {

    entityManagerFactory = Persistence.createEntityManagerFactory("jpastart");
  }

  public static EntityManager createEntityManager() {

    return entityManagerFactory.createEntityManager();
  }

  public static void close() {

    entityManagerFactory.close();
  }
}
