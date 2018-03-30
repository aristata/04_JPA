package jpastart.reserve.application;

import com.aristatait.model.User;
import com.aristatait.usermanager.service.FindService;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GetUserServiceTest {
  @Rule
  public DBTestResource resource = new DBTestResource();
  private FindService findService = new FindService();

  @Test
  public void userFound_thenReturn() throws Exception {
    Optional<User> userOpt = findService.findUser("madvirus@madvirus.net");
    assertTrue(userOpt.isPresent());
    User user = userOpt.get();
    assertThat(user.getEmail(), equalTo("madvirus@madvirus.net"));
    assertThat(user.getName(), equalTo("최범균"));
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    assertThat(dateFormat.format(user.getCreateDate()), equalTo("2016-06-05 01:02:03"));
  }


  @Test
  public void noUser_returnNull() throws Exception {
    Optional<User> userOpt = findService.findUser("madvirus2@madvirus.net");
    assertFalse(userOpt.isPresent());
  }
}
