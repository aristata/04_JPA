package com.aristatait.configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomEntityMangerFactory {

  private static EntityManagerFactory emf;

  // EntityManagerFactory를 생성하는 메소드
  public static void init() {
    emf = Persistence.createEntityManagerFactory("jpastart");
  }

  // EntityManager를 생성하여 반환하는 메소드
  public static EntityManager createEntityManager() {
    return emf.createEntityManager();
  }

  // EntityManagerFactory를 종료하는 메소드
  public static void close() {
    emf.close();
  }
}
