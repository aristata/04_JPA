package configuration;

import sun.reflect.generics.tree.VoidDescriptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomEntityManagerFactory {

  private static EntityManagerFactory entityManagerFactory;

  private static ThreadLocal<EntityManager> currentEntityManager = new ThreadLocal<>();

  public static void init() {
    entityManagerFactory = Persistence.createEntityManagerFactory("jpastart");
  }

  public static EntityManager createEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  public static void close() {
    entityManagerFactory.close();
  }

  public static EntityManager currentEntityManager() {
    EntityManager entityManager = currentEntityManager.get();
    if (entityManager == null) {
      entityManager = entityManagerFactory.createEntityManager();
      currentEntityManager.set(entityManager);
    }
    return entityManager;
  }

  public static void closeCurrentEntityManager() {
    EntityManager entityManager = currentEntityManager.get();
    if (entityManager != null) {
      currentEntityManager.remove();
      entityManager.close();
    }
  }
}
