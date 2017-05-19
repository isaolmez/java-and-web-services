package com.isa.rest.service.jaxrs.prediction.data;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "predictionsList")
public class PredictionsList {

  private List<Prediction> preds;

  private AtomicInteger predId;

  public PredictionsList() {
    preds = new CopyOnWriteArrayList<>();
    predId = new AtomicInteger();
  }

  @XmlElement
  @XmlElementWrapper(name = "predictions")
  public List<Prediction> getPredictions() {
    return this.preds;
  }

  public void setPredictions(List<Prediction> preds) {
    this.preds = preds;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    for (Prediction p : preds) {
      s.append(p.toString());
    }

    return s.toString();
  }

  public Prediction find(int id) {
    Prediction pred = null;
    for (Prediction p : preds) {
      if (p.getId() == id) {
        pred = p;
        break;
      }
    }

    return pred;
  }

  public int add(String who, String what) {
    int id = predId.incrementAndGet();
    Prediction p = new Prediction();
    p.setId(id);
    p.setWho(who);
    p.setWhat(what);
    preds.add(p);
    return id;
  }
}
