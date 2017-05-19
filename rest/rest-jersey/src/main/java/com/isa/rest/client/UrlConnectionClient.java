package com.isa.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlConnectionClient {
  public static void main(String[] args) {
    // usage
    if (args.length < 1) {
      System.err.println("Usage: UrlConnectionClient <url>");
      args = new String[] {"http://localhost:9091/servlet"};
      //			return;
    }
    try {
      // Parse the URL.
      URL url = new URL(args[0].trim());

      // Connect.
      URLConnection connection = url.openConnection();

      // Read and print the response
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String record;
      while ((record = reader.readLine()) != null) {
        System.out.println(record);
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
