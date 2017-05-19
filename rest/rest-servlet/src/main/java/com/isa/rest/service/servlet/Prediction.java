package com.isa.rest.service.servlet;

import java.io.Serializable;

// An array of Predictions is to be serialized
// into an XML or JSON document, which is returned to
// the consumer on a request.
@SuppressWarnings("serial")
public class Prediction implements Serializable, Comparable<Prediction> {
  private int id;

  private String who;

  private String what;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getWho() {
    return who;
  }

  public void setWho(String who) {
    this.who = who;
  }

  public String getWhat() {
    return what;
  }

  public void setWhat(String what) {
    this.what = what;
  }

  @Override
  public int compareTo(Prediction other) {
    return this.id - other.id;
  }
}
