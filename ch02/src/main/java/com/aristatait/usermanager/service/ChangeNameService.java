package com.aristatait.usermanager.service;

import com.aristatait.configuration.CustomEntityMangerFactory;
import com.aristatait.model.User;
import com.aristatait.usermanager.exception.UserNotFoundException;

import javax.persistence.EntityManager;

public class ChangeNameService {

  public void changeName(String email, String newName) {

    // EntityManger 생성
    EntityManager entityManager = CustomEntityMangerFactory.createEntityManager();

    try {

      // Transaction 시작
      entityManager.getTransaction().begin();

      // 객체 조회
      User user = entityManager.find(User.class, email);

      if (user == null) {
        throw new UserNotFoundException();
      }

      // user 이름 변경
      user.changeName(newName);

      // 커밋
      entityManager.getTransaction().commit();

      // ※ JPA 는 트랜잭션을 종료할 때 영속 컨텍스트에 존재하는 연속 객체의 값이 변경되었는지를 검사한다.
      // 만약 값이 바뀌었다면 변경된 값을 DB에 반영하기 위해 update 쿼리를 실행한다.
      // 이를 하이버네이트에서는 dirty checking 이라고 부른다.

    } catch (Exception ex) {

      // 롤백
      entityManager.getTransaction().rollback();
      throw ex;

    } finally {

      entityManager.close();
    }
  }
}
