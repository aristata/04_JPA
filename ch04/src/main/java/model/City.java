package model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class City {
  @Id
  @TableGenerator(name = "idgen",
    table = "id_gen",
    pkColumnName = "entity",
    pkColumnValue = "city",
    valueColumnName = "nextid",
    initialValue = 0,
    allocationSize = 1)
  @GeneratedValue(generator = "idgen")
  private Long id;

  private String name;

  //@Embeddable에 Embeddable을 중첩
  //ContactInfo 밸류에서도 설정이 가능함.
  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "address.zipcode", column = @Column(name = "ct_zip")),
    @AttributeOverride(name = "address.address1", column = @Column(name = "ct_addr1")),
    @AttributeOverride(name = "address.address2", column = @Column(name = "ct_addr2"))
  })
  private ContactInfo contactInfo;

  protected City() {
  }

  public City(String name) {
    this(name, null);
  }

  public City(String name, ContactInfo contactInfo) {
    this.name = name;
    this.contactInfo = contactInfo;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ContactInfo getContactInfo() {
    return contactInfo;
  }
}
