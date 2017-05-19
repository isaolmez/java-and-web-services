package com.isa.rest.service.jaxrs.prediction;

import org.glassfish.jersey.servlet.ServletProperties;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

public class PredictionResourceTest extends JerseyTest {

  private static final String PREDICTION_RESOURCE_XML = "/xml";

  private static final String PREDICTION_RESOURCE_JSON = "/json";

  @Override
  protected DeploymentContext configureDeployment() {
    return ServletDeploymentContext.builder(new PredictionApplication())
        .initParam(ServletProperties.JAXRS_APPLICATION_CLASS, PredictionApplication.class.getName())
        .build();
  }

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyWebTestContainerFactory();
  }

  @Test
  public void shouldGetAllPredictions_WithXmlRepresentation() {
    Response response =
        target(PREDICTION_RESOURCE_XML).request().accept(MediaType.APPLICATION_XML).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_XML_TYPE);
  }

  @Test
  public void shouldGetAllPredictions_WithJsonRepresentation() {
    Response response =
        target(PREDICTION_RESOURCE_JSON).request().accept(MediaType.APPLICATION_JSON).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
  }

  // TODO add other tests
}
