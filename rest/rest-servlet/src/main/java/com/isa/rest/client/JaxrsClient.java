package com.isa.rest.client;

import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

public class JaxrsClient {
  private static final String baseUrl = "http://localhost:9091/servlet";

  public static void main(String[] args) {
    new JaxrsClient().demo();
  }

  private void demo() {
    Client client =
        ClientBuilder.newBuilder().property(ClientProperties.FOLLOW_REDIRECTS, true).build();

    WebTarget resource = client.target(baseUrl);
    getAllDemo(resource);
    postDemo(resource); // same resource but different verb

    String url = baseUrl + "?id=32";
    resource = client.target(url);
    getOneDemo(resource);
    deleteDemo(resource); // delete id = 32
  }

  private void getAllDemo(WebTarget resource) {
    // GET all XML
    String response = resource.request(MediaType.APPLICATION_XML_TYPE).get(String.class);
    report("GET all in XML:\n", response);

    // GET all JSON
    response = resource.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
    report("GET all in JSON:\n", response);
  }

  private void getOneDemo(WebTarget resource) {
    String response = resource.request(MediaType.APPLICATION_XML_TYPE).get(String.class);
    report("GET one in XML:\n", response);

    response = resource.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
    report("GET one in JSON:\n", response);
  }

  private void postDemo(WebTarget resource) {
    Form form = new Form(); // HTTP body, a simple hash
    form.param("who", "William Butler Yeats");
    form.param("what", "I know that I shall meet my fate");

    String response =
        resource.request(MediaType.TEXT_PLAIN_TYPE).post(Entity.form(form), String.class);
    report("POST:\n", response);
  }

  private void deleteDemo(WebTarget resource) {
    String response = resource.request(MediaType.TEXT_PLAIN_TYPE).delete(String.class);
    report("DELETE:\n", response);
  }

  private void report(String msg, String response) {
    System.out.println("\n" + msg + response);
  }
}
