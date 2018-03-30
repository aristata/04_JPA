package jpastart.reserve.application;

import com.aristatait.usermanager.exception.UserNotFoundException;
import com.aristatait.usermanager.service.ChangeNameService;
import jpastart.reserve.model.UserDomain;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ChangeNameServiceTest {
  @Rule
  public DBTestResource resource = new DBTestResource();
  private ChangeNameService changeNameService = new ChangeNameService();

  @Test
  public void userFound_thenNameChaned() {
    changeNameService.changeName("madvirus@madvirus.net", "범균");
    UserDomain.assertName("madvirus@madvirus.net", "범균");
  }


  @Test
  public void noUser_throwEx() {
    try {
      changeNameService.changeName("nouser@madvirus.net", "범균");
      fail("익센셥 발생해야 함");
    } catch (UserNotFoundException e) {
    }
  }
}
