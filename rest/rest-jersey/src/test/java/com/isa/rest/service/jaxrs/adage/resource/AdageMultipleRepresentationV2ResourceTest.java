package com.isa.rest.service.jaxrs.adage.resource;

import com.isa.rest.service.jaxrs.adage.AdageApplication;
import com.isa.rest.service.jaxrs.adage.data.Adage;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;

public class AdageMultipleRepresentationV2ResourceTest extends JerseyTest {

  private static final String MULTI_REP_RESOURCE = "/multiv2";

  private static final String WORDS_VALUE = "For Test";

  private static final int WORD_COUNT = 2;

  private static final String ADAGE_VALUE = "For Test -- 2 words";

  @Override
  protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
    return new GrizzlyTestContainerFactory();
  }

  @Override
  protected Application configure() {
    enable(TestProperties.LOG_TRAFFIC);
    enable(TestProperties.DUMP_ENTITY);
    return new AdageApplication();
  }

  @Test
  public void shouldGetAdage_WithHtmlRepresentation_WithDefaultAcceptHeader() {
    Response response = target(MULTI_REP_RESOURCE).request().get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_HTML_TYPE);
    String entity = response.readEntity(String.class);
    assertThat(entity).isEqualTo(ADAGE_VALUE);
  }

  @Test
  public void shouldGetAdage_WithXmlRepresentation_WhenXmlIsRequested() {
    Response response = target(MULTI_REP_RESOURCE).request(MediaType.APPLICATION_XML).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_XML_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldGetAdage_WithJsonRepresentation_WhenJsonIsRequested() {
    Response response = target(MULTI_REP_RESOURCE).request(MediaType.APPLICATION_JSON).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldGetAdage_WithXmlRepresentation_WhenXmlAndJsonIsRequested() {
    Response response =
        target(MULTI_REP_RESOURCE)
            .request(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON)
            .get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_XML_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldGetAdage_WithJsonRepresentation_WhenJsonIsPrioritized() {
    Response response =
        target(MULTI_REP_RESOURCE)
            .request(MediaType.APPLICATION_XML + ";q=0.9", MediaType.APPLICATION_JSON)
            .get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.APPLICATION_JSON_TYPE);
    Adage entity = response.readEntity(Adage.class);
    assertThat(entity.getWords()).isEqualTo(WORDS_VALUE);
    assertThat(entity.getWordCount()).isEqualTo(WORD_COUNT);
  }

  @Test
  public void shouldGetAdage_WithPlainTextRepresentation_WhenPlainTextIsRequested() {
    Response response = target(MULTI_REP_RESOURCE).request(MediaType.TEXT_PLAIN).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_PLAIN_TYPE);
    String entity = response.readEntity(String.class);
    assertThat(entity).isEqualTo(ADAGE_VALUE);
  }

  @Test
  public void shouldGetAdage_WithHtmlRepresentation_WhenHtmlIsRequested() {
    Response response = target(MULTI_REP_RESOURCE).request(MediaType.TEXT_HTML).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_HTML_TYPE);
    String entity = response.readEntity(String.class);
    assertThat(entity).isEqualTo(ADAGE_VALUE);
  }

  @Test
  public void shouldGetAdage_WithPlainTextRepresentation_WhenPlainAndHtmlIsRequested() {
    Response response =
        target(MULTI_REP_RESOURCE).request(MediaType.TEXT_PLAIN, MediaType.TEXT_HTML).get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_PLAIN_TYPE);
    String entity = response.readEntity(String.class);
    assertThat(entity).isEqualTo(ADAGE_VALUE);
  }

  @Test
  public void shouldGetAdage_WithHtmlRepresentation_WhenHtmlIsPrioritized() {
    Response response =
        target(MULTI_REP_RESOURCE)
            .request(MediaType.TEXT_PLAIN + ";q=0.9", MediaType.TEXT_HTML)
            .get();

    assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
    assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_HTML_TYPE);
    String entity = response.readEntity(String.class);
    assertThat(entity).isEqualTo(ADAGE_VALUE);
  }
}
