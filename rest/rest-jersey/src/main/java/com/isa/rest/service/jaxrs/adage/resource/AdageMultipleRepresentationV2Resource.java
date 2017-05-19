package com.isa.rest.service.jaxrs.adage.resource;

import com.isa.rest.service.jaxrs.adage.data.Adage;
import com.isa.rest.service.jaxrs.adage.data.AdageFactory;
import com.isa.rest.service.jaxrs.adage.data.DefaultAdageFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/multiv2")
public class AdageMultipleRepresentationV2Resource {
  private final AdageFactory adageFactory;

  public AdageMultipleRepresentationV2Resource() {
    this.adageFactory = new DefaultAdageFactory();
  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response getXml() {
    Adage adage = adageFactory.sameAdage();
    return Response.status(Response.Status.OK).entity(adage).build();
  }

  @GET
  @Produces({MediaType.TEXT_PLAIN, MediaType.TEXT_HTML})
  public Response getPlain() {
    Adage adage = adageFactory.sameAdage();
    return Response.status(Response.Status.OK).entity(adage.toString()).build();
  }
}
