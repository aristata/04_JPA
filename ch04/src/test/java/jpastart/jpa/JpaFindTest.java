package jpastart.jpa;

import configuration.CustomEntityManagerFactory;
import model.Hotel;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class JpaFindTest extends JpaTestBase {

  @Test
  public void findNotNullEmbeddedValue() {
    Hotel hotel = findHotelById("H100-01");
    assertThat(hotel.getAddress(), notNullValue());
  }

  @Test
  public void findNullEmbeddedValue() {
    Hotel hotel = findHotelById("H100-02");
    assertThat(hotel.getAddress(), nullValue());
  }

  private Hotel findHotelById(String hotelId) {
    Hotel room = null;
    EntityManager entityManager = CustomEntityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      room = entityManager.find(Hotel.class, hotelId);
      transaction.commit();
    } catch (Exception ex) {
      transaction.rollback();
      throw ex;
    } finally {
      entityManager.close();
    }
    return room;
  }

}

