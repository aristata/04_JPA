package jpastart.util;

import com.aristatait.configuration.CustomEntityMangerFactory;
import org.junit.rules.ExternalResource;

public class DBTestResource extends ExternalResource {
  @Override
  protected void before() throws Throwable {
    DBUtil.initTestData();
    CustomEntityMangerFactory.init();
  }

  @Override
  protected void after() {
    CustomEntityMangerFactory.close();
  }
}
