package com.aristatait.usermanager.service;

import com.aristatait.configuration.CustomEntityMangerFactory;
import com.aristatait.model.User;
import com.aristatait.usermanager.exception.DuplicateEmailException;

import javax.persistence.EntityManager;

public class JoinService {

  public void join(User user) {

    // EntityManager 생성
    EntityManager entityManager = CustomEntityMangerFactory.createEntityManager();

    // EntityTransaction 시작
    entityManager.getTransaction().begin();

    try {

      // 식별자를 이용해서 User 객체를 찾는다.
      User found = entityManager.find(User.class, user.getEmail());

      // 발견된 객체가 있으면 이메일 중복 예외를 던진다.
      if (found != null) {
        throw new DuplicateEmailException();
      }

      // user 객체를 등록한다.
      entityManager.persist(user);

      // transaction 을 커밋한다.
      entityManager.getTransaction().commit();

    } catch (Exception ex) {

      // 진행중에 예외가 발생하면 transaction 을 롤백한다.
      entityManager.getTransaction().rollback();
      throw ex;

    } finally {

      // EntityManager를 종료한다.
      entityManager.close();
    }
  }
}
