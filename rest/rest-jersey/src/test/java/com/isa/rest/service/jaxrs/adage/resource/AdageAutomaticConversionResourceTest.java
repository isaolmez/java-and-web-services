package com.isa.rest.service.jaxrs.adage.resource;

import com.isa.rest.service.jaxrs.adage.AdageApplication;
import com.isa.rest.service.jaxrs.adage.data.Adage;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

public class AdageAutomaticConversionResourceTest extends JerseyTest {

  private static final String AUTO_RESOURCE = "/auto";

  private static final String AUTO_RESOURCE_JSON = "/auto/json";

  private static final String AUTO_RESOURCE_PLAIN = "/auto/plain";

  private static final String WORDS_VALUE = "For Test";

  private static final int WORD_COUNT = 2;

  private static final String ADAGE_VALUE = "For Test -- 2 words";

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  @Override
  protected void configureClient(ClientConfig config) {
    config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_CLIENT, "INFO");
  }

  @Override
  protected Application configure() {
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    set(TestProperties.CONTAINER_PORT, 9091);
    return new AdageApplication();
  }

  @Test
  public void shouldGetAdage_WithXmlRepresentation_WithDefaultAcceptHeader() {
    Response response = target(AUTO_RESOURCE).request().get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_XML_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldGetAdage_WithXmlRepresentation_WhenXmlIsRequested() {
    Response response = target(AUTO_RESOURCE).request(MediaType.APPLICATION_XML).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_XML_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldNotGetAdage_WithXmlRepresentation_WhenJsonIsRequested() {
    Response response = target(AUTO_RESOURCE).request(MediaType.APPLICATION_JSON).get();

    assertThat(response.getStatus()).isEqualTo(NOT_ACCEPTABLE.getStatusCode());
  }

  @Test
  public void shouldNotGetAdage_WithXmlRepresentation_WhenPlainTextIsRequested() {
    Response response = target(AUTO_RESOURCE).request(MediaType.APPLICATION_JSON).get();

    assertThat(response.getStatus()).isEqualTo(NOT_ACCEPTABLE.getStatusCode());
  }

  @Test
  public void shouldGetAdage_WithJsonRepresentation_WithDefaultAcceptHeader() {
    Response response = target(AUTO_RESOURCE_JSON).request().get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldGetAdage_WithJsonRepresentation_WhenJsonIsRequested() {
    Response response = target(AUTO_RESOURCE_JSON).request(MediaType.APPLICATION_JSON).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldNotGetAdage_WithJsonRepresentation_WhenXmlIsRequested() {
    Response response = target(AUTO_RESOURCE_JSON).request(MediaType.APPLICATION_XML).get();

    assertThat(response.getStatus()).isEqualTo(NOT_ACCEPTABLE.getStatusCode());
  }

  @Test
  public void shouldNotGetAdage_WithJsonRepresentation_WhenPlainTextIsRequested() {
    Response response = target(AUTO_RESOURCE_JSON).request(MediaType.TEXT_PLAIN).get();

    assertThat(response.getStatus()).isEqualTo(NOT_ACCEPTABLE.getStatusCode());
  }

  @Test
  public void shouldGetAdage_WithPlainTextRepresentation_WithDefaultAcceptHeader() {
    Response response = target(AUTO_RESOURCE_PLAIN).request().get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_PLAIN_TYPE);
    String entity = response.readEntity(String.class);
    assertThat(entity).isEqualTo(ADAGE_VALUE);
  }

  @Test
  public void shouldGetAdage_WithPlainTextRepresentation_WhenPlainTextIsRequested() {
    Response response = target(AUTO_RESOURCE_PLAIN).request(MediaType.TEXT_PLAIN).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_PLAIN_TYPE);
    String entity = response.readEntity(String.class);
    assertThat(entity).isEqualTo(ADAGE_VALUE);
  }

  @Test
  public void shouldNotGetAdage_WithPlainTextRepresentation_WhenXmlIsRequested() {
    Response response = target(AUTO_RESOURCE_PLAIN).request(MediaType.APPLICATION_XML).get();

    assertThat(response.getStatus()).isEqualTo(NOT_ACCEPTABLE.getStatusCode());
  }

  @Test
  public void shouldNotGetAdage_WithPlainTextRepresentation_WhenJsonIsRequested() {
    Response response = target(AUTO_RESOURCE_PLAIN).request(MediaType.APPLICATION_JSON).get();

    assertThat(response.getStatus()).isEqualTo(NOT_ACCEPTABLE.getStatusCode());
  }
}
