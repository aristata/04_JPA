package com.aristatait.adduser;

import com.aristatait.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * Persistence Context를 이용하여 User 객체를 DB에 삽입하는 예제
 * EntityManagerFactory, EntityManager, EntityTransaction 등을 사용한다.
 */
public class AddUserMain {

  public static void main(String[] args) {

    // EntityManagerFactory 생성
    // EntityManagerFactory 는 persistence(영속) unit 별로 EntityManager 를 생성하는 역할을 한다.
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpastart");

    // EntityManager 생성
    // EntityManager 는 영속 컨텍스트와 엔티티를 관리한다.
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // EntityTransaction
    EntityTransaction entityTransaction = entityManager.getTransaction();

    try {

      // transaction 시작
      entityTransaction.begin();

      // User 객체 생성
      User user = new User("user@user.com", "user", new Date());

      // Persistence Context 에 User 객체 등록
      entityManager.persist(user);

      // transaction 커밋
      entityTransaction.commit();

    } catch (Exception exception) {

      // 예외 발생시 예외를 프린트 한다.
      exception.printStackTrace();

      // transaction 을 롤백 한다.
      entityTransaction.rollback();

    } finally {

      // EntityManager 를 종료한다.
      entityManager.close();
    }

    // EntityManagerFactory 를 종료한다.
    entityManagerFactory.close();
  }
}
