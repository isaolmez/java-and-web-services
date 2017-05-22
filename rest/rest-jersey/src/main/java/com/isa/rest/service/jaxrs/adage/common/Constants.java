package com.isa.rest.service.jaxrs.adage.common;

public class Constants {
  private Constants() {}

  public static final String EMPTY = "";

  public static class Adage {
    private Adage() {}

    public static final int PORT = 9091;

    public static final int BACKLOG = 8;

    public static final String HOSTNAME = "localhost";

    public static final String RESOURCE_CONTEXT = "/adage";

    public static final String RESOURCE_URL = "http://localhost:" + PORT + RESOURCE_CONTEXT;
  }

  public static class Prediction {
    private Prediction() {}

    public static final int PORT = 9091;

    public static final String HOSTNAME = "localhost";

    public static final String RESOURCE_CONTEXT = "/adage";

    public static final String RESOURCE_URL = "http://localhost:" + PORT + RESOURCE_CONTEXT;
  }
}
