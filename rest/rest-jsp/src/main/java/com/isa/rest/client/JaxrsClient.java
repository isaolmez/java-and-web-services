package com.isa.rest.client;

import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class JaxrsClient {
  private static final String baseUrl = "http://localhost:9091/";

  public static void main(String[] args) {
    new JaxrsClient().start();
  }

  private void start() {
    Client client =
        ClientBuilder.newBuilder().property(ClientProperties.FOLLOW_REDIRECTS, true).build();

    WebTarget target = client.target(baseUrl);
    getPredictions(target);
  }

  private void getPredictions(WebTarget target) {
    String response = target.request(MediaType.APPLICATION_XML_TYPE).get(String.class);
    report("GET all in XML:\n", response);
  }

  private void report(String msg, String response) {
    System.out.println("\n" + msg + response);
  }
}
