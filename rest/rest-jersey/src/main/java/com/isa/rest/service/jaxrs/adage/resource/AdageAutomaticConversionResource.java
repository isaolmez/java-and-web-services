package com.isa.rest.service.jaxrs.adage.resource;

import com.isa.rest.service.jaxrs.adage.data.Adage;
import com.isa.rest.service.jaxrs.adage.data.AdageFactory;
import com.isa.rest.service.jaxrs.adage.data.DefaultAdageFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/auto")
public class AdageAutomaticConversionResource {
  private final AdageFactory adageFactory;

  public AdageAutomaticConversionResource() {
    this.adageFactory = new DefaultAdageFactory();
  }

  @GET
  @Produces({MediaType.APPLICATION_XML})
  public Adage getXml() {
    return adageFactory.sameAdage();
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/json")
  public Adage getJson() {
    return adageFactory.sameAdage();
  }

  @GET
  @Produces({MediaType.TEXT_PLAIN})
  @Path("/plain")
  public String getPlain() {
    return adageFactory.sameAdage().toString();
  }
}
