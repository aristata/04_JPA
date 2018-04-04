package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SightDetail {

  @Column(name = "hours_op", table = "sight_detail")
  private String hoursOfOperation;

  @Column(table = "sight_detail")
  private String holidays;

  @Column(table = "sight_detail")
  private String facilities;

  public SightDetail() {
  }

  public SightDetail(String hoursOfOperation, String holidays, String facilities) {
    this.hoursOfOperation = hoursOfOperation;
    this.holidays = holidays;
    this.facilities = facilities;
  }

  public String getHoursOfOperation() {
    return hoursOfOperation;
  }

  public void setHoursOfOperation(String hoursOfOperation) {
    this.hoursOfOperation = hoursOfOperation;
  }

  public String getHolidays() {
    return holidays;
  }

  public void setHolidays(String holidays) {
    this.holidays = holidays;
  }

  public String getFacilities() {
    return facilities;
  }

  public void setFacilities(String facilities) {
    this.facilities = facilities;
  }
}
