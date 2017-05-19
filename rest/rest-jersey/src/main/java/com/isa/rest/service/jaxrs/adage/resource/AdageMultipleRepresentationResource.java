package com.isa.rest.service.jaxrs.adage.resource;

import com.isa.rest.service.jaxrs.adage.data.Adage;
import com.isa.rest.service.jaxrs.adage.data.AdageFactory;
import com.isa.rest.service.jaxrs.adage.data.DefaultAdageFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/multi")
public class AdageMultipleRepresentationResource {
  private final AdageFactory adageFactory;

  public AdageMultipleRepresentationResource() {
    this.adageFactory = new DefaultAdageFactory();
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Adage getXml() {
    return adageFactory.sameAdage();
  }

  @GET
  @Produces({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML})
  public String getPlain() {
    return adageFactory.sameAdage().toString();
  }
}
