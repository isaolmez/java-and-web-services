package com.isa.rest.service.jsp;

import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

public class Predictions {

  private static final String PREDICTIONS_KEY = "predictions";

  private List<Prediction> predictions;

  private ServletContext servletContext;

  public void setServletContext(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public ServletContext getServletContext() {
    return this.servletContext;
  }

  public void setPredictions(String predictions) {} // no-op

  public List<Prediction> getPredictions() {
    return getPredictionsInternal();
  }

  public String getPredictionsAsXML() {
    return toXML(getPredictions());
  }

  private List<Prediction> getPredictionsInternal() {
    if (predictions == null) {
      // Has the ServletContext been set?
      if (getServletContext() == null) {
        return null;
      }

      List<Prediction> result =
          (List<Prediction>) getServletContext().getAttribute(PREDICTIONS_KEY);
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
      predictions = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
        String record;
        while ((record = reader.readLine()) != null) {
          String[] parts = record.split("!");
          Prediction p = new Prediction();
          p.setWho(parts[0]);
          p.setWhat(parts[1]);
          predictions.add(p);
        }
      } catch (IOException e) {
        throw new RuntimeException("Error occurred during read.", e);
      }
    }
  }

  private String toXML(List<Prediction> source) {
    String xml;
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      XMLEncoder encoder = new XMLEncoder(out);
      encoder.writeObject(source.toArray(new Prediction[0])); // serialize to XML
      encoder.close();
      xml = out.toString(); // stringify
    } catch (IOException e) {
      throw new RuntimeException("Error occurred during serialization", e);
    }

    return xml;
  }
}
