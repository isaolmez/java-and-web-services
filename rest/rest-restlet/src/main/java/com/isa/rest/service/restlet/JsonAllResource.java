package com.isa.rest.service.restlet;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;

public class JsonAllResource extends ServerResource {

  @Get
  public Representation toJson() {
    List<Adage> list = Adages.getList();

    // Generate the JSON representation.
    JsonRepresentation json = null;
    try {
      json = new JsonRepresentation(new StringRepresentation(list.toString()));
    } catch (Exception e) {
    }
    return json;
  }
}
