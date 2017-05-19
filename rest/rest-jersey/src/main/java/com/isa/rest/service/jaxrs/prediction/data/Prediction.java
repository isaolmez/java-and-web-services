package com.isa.rest.service.jaxrs.prediction.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "prediction")
public class Prediction implements Comparable<Prediction> {
  private int id;

  private String who;

  private String what;

  @XmlElement
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @XmlElement
  public String getWho() {
    return this.who;
  }

  public void setWho(String who) {
    this.who = who;
  }

  @XmlElement
  public String getWhat() {
    return this.what;
  }

  public void setWhat(String what) {
    this.what = what;
  }

  @Override
  public int compareTo(Prediction other) {
    return this.id - other.id;
  }

  @Override
  public String toString() {
    return String.format("%2d: ", id) + who + " ==> " + what + "\n";
  }
}
