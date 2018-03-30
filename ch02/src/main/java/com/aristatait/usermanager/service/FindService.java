package com.aristatait.usermanager.service;

import com.aristatait.configuration.CustomEntityMangerFactory;
import com.aristatait.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class FindService {

  /**
   *  email을 받아와서 User를 조회하는 메서드
   */
  public Optional<User> findUser(String email) {

    EntityManager entityManager = CustomEntityMangerFactory.createEntityManager();

    // ※ 단순 조회기능만 구현하기 때문에 transaction 이 필요가 없다.

    try {

      User user = entityManager.find(User.class, email);

      // user 객체를 값으로 갖는 Optional 객체를 반환한다.
      return Optional.ofNullable(user);

    } finally {

      entityManager.close();
    }
  }

  /**
   *  List를 조회하는 메서드
   */
  public List<User> findAll() {

    EntityManager entityManager = CustomEntityMangerFactory.createEntityManager();

    try {
      entityManager.getTransaction().begin();

      // JPQL(Java Persistence Query Language)를 실행하기 위한 TypedQuery를 생성한다.
      TypedQuery<User> query = entityManager.createQuery("select u from User u order by u.name", User.class);

      // TypedQuery를 이용해서 쿼리 실행 결과를 구한다.
      List<User> result = query.getResultList();

      entityManager.getTransaction().commit();

      return result;

    } catch (Exception ex) {

      entityManager.getTransaction().rollback();
      throw ex;

    } finally {

      entityManager.close();
    }
  }
}
