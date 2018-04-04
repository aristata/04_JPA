package model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(catalog = "jpastart", name = "hotel")
public class Hotel {

  @Id
  private String id;

  private String name;

  //열거 타입에 대한 매핑은 @javax.persistence.Enumerated 애노테이션을 사용한다.
  //값으로 EnumType.STRING과 EnumType.ORDINAL을 사용할 수 있는데,
  //필드의 값이 열거 타입의 상수 이름을 사용할 경우에는 EnumType.STRING을 사용하고,
  //인덱스를 사용할 경우에는 EnumType.ORDINAL을 사용한다.
  @Enumerated(value = EnumType.STRING)
  private Grade grade;

  //@Embedded 클래스는 매핑 대상이 @Embeddable 클래스의 인스턴스라는 것을 설정한다.
  //이 설정을 사용하면 address 필드의 매핑을 처리할 때 Address에 설정된 매핑 정보를 이용한다.
  @Embedded
  private Address address;

  // JPA Entity를 작성할 때에는 빈 생성자를 꼭 작성해야 한다.
  protected Hotel() {
  }

  public Hotel(String id, String name, Grade grade, Address address) {
    this.id = id;
    this.name = name;
    this.grade = grade;
    this.address = address;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Grade getGrade() {
    return grade;
  }

  public Address getAddress() {
    return address;
  }

  public void changeAddress(Address newAddress) {
    this.address = newAddress;
  }
}
