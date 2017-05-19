package com.isa.rest.service.servlet;

import java.beans.XMLEncoder; // simple and effective
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;

public class Predictions {
  private static final String PREDICTIONS_KEY = "predictions";

  private ConcurrentMap<Integer, Prediction> predictions;

  private ServletContext servletContext;

  private AtomicInteger mapKey;

  public Predictions() {
    predictions = new ConcurrentHashMap<>();
    mapKey = new AtomicInteger();
  }

  // The ServletContext is required to read the data from
  // a text file packaged inside the WAR file
  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public ServletContext getServletContext() {
    return this.servletContext;
  }

  public ConcurrentMap<Integer, Prediction> getPredictions() {
    return getPredictionsInternal();
  }

  public String getPredictionsAsXML() {
    return toXML(getPredictions());
  }

  public String toXML(Object obj) {
    String xml;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      XMLEncoder encoder = new XMLEncoder(out);
      encoder.writeObject(obj); // serialize to XML
      encoder.close();
      xml = out.toString(); // stringify
    } catch (IOException e) {
      throw new RuntimeException("Error occurred during serialization", e);
    }

    return xml;
  }

  public Prediction getPrediction(Integer key) {
    return predictions.get(key);
  }

  public int addPrediction(Prediction p) {
    int id = mapKey.incrementAndGet();
    p.setId(id);
    predictions.putIfAbsent(id, p);
    return id;
  }

  public void removePrediction(Integer key) {
    predictions.remove(key);
  }

  private ConcurrentMap<Integer, Prediction> getPredictionsInternal() {
    if (predictions.isEmpty()) {
      // Has the ServletContext been set?
      if (getServletContext() == null) {
        return null;
      }

      ConcurrentMap<Integer, Prediction> result =
          (ConcurrentMap<Integer, Prediction>) getServletContext().getAttribute(PREDICTIONS_KEY);
      if (result != null) {
        predictions = result;
      } else {
        populateFromFile();
        storeInServletContext(PREDICTIONS_KEY, predictions);
      }
    }

    return predictions;
  }

  private void storeInServletContext(String key, Object value) {
    getServletContext().setAttribute(key, value);
  }

  private void populateFromFile() {
    String filename = "/WEB-INF/classes/data/predictions.db";
    InputStream in = servletContext.getResourceAsStream(filename);

    // Read the data into the array of Predictions.
    if (in != null) {
      try {
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(isr);

        String record;
        while ((record = reader.readLine()) != null) {
          String[] parts = record.split("!");
          Prediction p = new Prediction();
          p.setWho(parts[0]);
          p.setWhat(parts[1]);
          addPrediction(p);
        }
      } catch (IOException e) {
        throw new RuntimeException("Error occurred when reading data", e);
      }
    }
  }
}
