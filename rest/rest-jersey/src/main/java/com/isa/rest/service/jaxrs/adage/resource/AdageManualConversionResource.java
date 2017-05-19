package com.isa.rest.service.jaxrs.adage.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.rest.service.jaxrs.adage.data.Adage;
import com.isa.rest.service.jaxrs.adage.data.AdageFactory;
import com.isa.rest.service.jaxrs.adage.data.DefaultAdageFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;

/** Manual conversion for representations */
@Path("/manual")
public class AdageManualConversionResource {

  private final AdageFactory adageFactory;

  public AdageManualConversionResource() {
    this.adageFactory = new DefaultAdageFactory();
  }

  @GET
  @Produces({MediaType.APPLICATION_XML})
  public JAXBElement<Adage> getXml() {
    return toXml(adageFactory.sameAdage());
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/json")
  public String getJson() {
    return toJson(adageFactory.sameAdage());
  }

  @GET
  @Produces({MediaType.TEXT_PLAIN})
  @Path("/plain")
  public String getPlain() {
    return adageFactory.sameAdage().toString();
  }

  /** Helper Methods */
  // Java Adage --> XML document
  @XmlElementDecl(namespace = "http://aphorism.adage", name = "adage")
  private JAXBElement<Adage> toXml(Adage adage) {
    return new JAXBElement<>(new QName("adage"), Adage.class, adage);
  }

  // Java Adage --> JSON document
  // Jersey provides automatic conversion to JSON using the Jackson
  // libraries. In this example, the conversion is done manually
  // with the Jackson libraries just to indicate how straightforward it is.
  private String toJson(Adage adage) {
    String json;
    try {
      json = new ObjectMapper().writeValueAsString(adage);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error occurred when serializing", e);
    }

    return json;
  }
}
