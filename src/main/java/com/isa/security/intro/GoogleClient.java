package com.isa.security;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

public class GoogleClient {
  private static final String endpoint = "https://www.google.com:443";

  public static void main(String[] args) {
    GoogleClient googleClient = new GoogleClient();
    googleClient.doIt();
  }

  private void doIt() {
    try {
      URL url = new URL(endpoint);
      HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.setRequestMethod("GET");
      connection.connect();
      dumpDetails(connection);

    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void dumpDetails(HttpsURLConnection connection) {
    try {
      System.out.println(connection.getResponseCode());
      System.out.println(connection.getCipherSuite());
      Certificate[] serverCertificates = connection.getServerCertificates();
      for (Certificate certificate : serverCertificates) {
        System.out.println("\tCert. type: " + certificate.getType());
        System.out.println("\tHash code:  " + certificate.hashCode());
        System.out.println("\tAlgorithm:  " + certificate.getPublicKey().getAlgorithm());
        System.out.println("\tFormat:     " + certificate.getPublicKey().getFormat());
        System.out.println("");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
