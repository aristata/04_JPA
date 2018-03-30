package com.aristatait.usermanager.service;

import com.aristatait.configuration.CustomEntityMangerFactory;
import com.aristatait.model.User;
import com.aristatait.usermanager.exception.UserNotFoundException;

import javax.persistence.EntityManager;

public class WithdrawService {

  public void withdraw(String email) {

    EntityManager entityManager = CustomEntityMangerFactory.createEntityManager();

    entityManager.getTransaction().begin();

    try {

      User user = entityManager.find(User.class, email);

      if (user == null) {
        throw new UserNotFoundException();
      }

      entityManager.remove(user);

      entityManager.getTransaction().commit();

    } catch (Exception ex) {

      entityManager.getTransaction().rollback();
      throw ex;

    } finally {

      entityManager.close();
    }
  }
}
