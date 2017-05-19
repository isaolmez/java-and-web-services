package com.isa.rest.service.jaxrs.adage;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;

import static com.isa.rest.service.jaxrs.adage.common.Constants.Adage.*;

/* Standalone Java application to publish the 'adages' JAX-RS service.
This is an alternative, typically for development, to publishing with
a web server such as Tomcat or Jetty. */
public class AdagePublisher {

  public static void main(String[] args) {
    new AdagePublisher().publish();
  }

  private void publish() {
    HttpServer server = getServer();
    HttpHandler requestHandler =
        RuntimeDelegate.getInstance().createEndpoint(new AdageApplication(), HttpHandler.class);
    server.createContext(RESOURCE_CONTEXT, requestHandler);
    server.start();
    msg(server);
  }

  private HttpServer getServer() {
    HttpServer server;
    try {
      server = HttpServer.create(new InetSocketAddress(HOSTNAME, PORT), BACKLOG);
    } catch (IOException e) {
      throw new RuntimeException("Cannot create the server", e);
    }

    return server;
  }

  private void msg(HttpServer server) {
    String out = "Publishing RestfulAdage on " + RESOURCE_URL + ". Hit any key to stop.";
    System.out.println(out);
    try {
      System.in.read();
    } catch (IOException e) {
      throw new RuntimeException("Error occurred", e);
    }

    server.stop(0); // normal termination
  }
}
