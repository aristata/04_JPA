package model;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.util.Objects;

//@Embeddable 애노테이션은 대상 클래스가 다른 엔티티의 일부로 함께 저장될 수 있다는 것을 설정한다.
@Embeddable
public class Address {
  @Basic
  private String zipcode;
  private String address1;
  private String address2;

  protected Address() {
  }

  public Address(String zipcode, String address1, String address2) {
    this.zipcode = zipcode;
    this.address1 = address1;
    this.address2 = address2;
  }

  public String getZipcode() {
    return zipcode;
  }

  public String getAddress1() {
    return address1;
  }

  public String getAddress2() {
    return address2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(zipcode, address.zipcode) &&
      Objects.equals(address1, address.address1) &&
      Objects.equals(address2, address.address2);
  }

  @Override
  public int hashCode() {

    return Objects.hash(zipcode, address1, address2);
  }

  @Override
  public String toString() {
    return "Address{" +
      "zipcode='" + zipcode + '\'' +
      ", address1='" + address1 + '\'' +
      ", address2='" + address2 + '\'' +
      '}';
  }
}
