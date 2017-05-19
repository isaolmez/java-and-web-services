package com.isa.security;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class GoogleTrustingClient {
  private static final String endpoint = "https://www.google.com:443";

  public static void main(String[] args) {
    GoogleTrustingClient googleClient = new GoogleTrustingClient();
    googleClient.doIt();
  }

  private void doIt() {
    try {
      // All trusting ssl context
      SSLContext sslContext = SSLContext.getInstance("TLS");
      TrustManager[] trustManagers = getTrustManagers();
      sslContext.init(null, trustManagers, new SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

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
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private TrustManager[] getTrustManagers() {
    return new TrustManager[] {
      new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return new X509Certificate[0];
        }
      }
    };
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
