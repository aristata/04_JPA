package jpastart.util;

import configuration.CustomEntityManagerFactory;
import org.junit.rules.ExternalResource;

public class DBTestResource extends ExternalResource {
  @Override
  protected void before() {
    DBUtil.initTestData();
    CustomEntityManagerFactory.init();
  }

  @Override
  protected void after() {
    CustomEntityManagerFactory.close();
  }
}
