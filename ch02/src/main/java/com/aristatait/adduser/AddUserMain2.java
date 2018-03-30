package com.aristatait.adduser;

import com.aristatait.configuration.CustomEntityMangerFactory;
import com.aristatait.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;

/**
 * CustomEntityManagerFactory를 사용하여 AddUserMain 코드를 간결하게 수정
 */
public class AddUserMain2 {

  public static void main(String[] args) {

    // EntityManagerFactory 생성
    CustomEntityMangerFactory.init();

    // EntityManager 호출
    EntityManager entityManager = CustomEntityMangerFactory.createEntityManager();

    // EntityTransaction
    EntityTransaction entityTransaction = entityManager.getTransaction();

    try {

      // transaction 시작
      entityTransaction.begin();

      // User 객체 생성
      User user = new User("user2@user.com", "user", new Date());

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
    CustomEntityMangerFactory.close();
  }
}
