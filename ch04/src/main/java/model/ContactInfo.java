package model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class ContactInfo {

  @Column(name = "ct_phone")
  private String phone;

  @Column(name = "ct_email")
  private String email;

  //@Embeddable에 Embeddable을 중첩
  //City 엔티티에서도 설정이 가능함.
  @Embedded
//  @AttributeOverrides({
//    @AttributeOverride(name = "zipcode", column = @Column(name = "ct_zip")),
//    @AttributeOverride(name = "address1", column = @Column(name = "ct_addr1")),
//    @AttributeOverride(name = "address2", column = @Column(name = "ct_addr2")),
//  })
  private Address address;

  public ContactInfo() {
  }

  public ContactInfo(String phone, String email, Address address) {
    this.phone = phone;
    this.email = email;
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public Address getAddress() {
    return address;
  }
}
