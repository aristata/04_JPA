package jpastart.reserve.application;

import com.aristatait.model.User;
import com.aristatait.usermanager.service.FindService;
import jpastart.util.DBTestResource;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GetUserListTest {
  @Rule
  public DBTestResource resource = new DBTestResource();
  private FindService findService = new FindService();

  @Test
  public void userList() {
    List<User> users = findService.findAll();
    assertThat(users.size(), equalTo(1));
  }
}
