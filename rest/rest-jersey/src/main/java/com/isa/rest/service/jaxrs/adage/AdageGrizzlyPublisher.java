package com.isa.rest.service.jaxrs.adage;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.utils.Charsets;

import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import static com.isa.rest.service.jaxrs.adage.common.Constants.Adage.*;

public class AdageGrizzlyPublisher {
  public static void main(String[] args) {
    new AdageGrizzlyPublisher().publish();
  }

  private void publish() {
    HttpServer server = getServer();

    configureServer(server);

    try {
      server.start();
    } catch (IOException e) {
      throw new RuntimeException("Cannot start server", e);
    }

    msg(server);
  }

  private HttpServer getServer() {
    SocketAddress inetSocketAddress = new InetSocketAddress(HOSTNAME, PORT);
    return HttpServer.createSimpleServer(RESOURCE_CONTEXT, inetSocketAddress);
  }

  private void configureServer(HttpServer server) {
    HttpHandler requestHandler =
        RuntimeDelegate.getInstance().createEndpoint(new AdageApplication(), HttpHandler.class);

    ServerConfiguration config = server.getServerConfiguration();
    config.addHttpHandler(requestHandler, RESOURCE_CONTEXT);
    config.setDefaultQueryEncoding(Charsets.UTF8_CHARSET);
  }

  private void msg(HttpServer server) {
    String out = "Publishing RestfulAdage on " + RESOURCE_URL + ". Hit any key to stop.";
    System.out.println(out);
    try {
      System.in.read();
    } catch (Exception e) {
    }

    server.shutdown();
  }
}
