package com.isa.rest.service.jaxrs.prediction.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.rest.service.jaxrs.prediction.data.Prediction;
import com.isa.rest.service.jaxrs.prediction.data.PredictionsList;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Path("/")
public class PredictionResource {
  @Context private ServletContext servletContext;

  private static PredictionsList plist;

  @GET
  @Path("/xml")
  @Produces({MediaType.APPLICATION_XML})
  public Response getXml() {
    checkContext();
    return Response.ok(plist, MediaType.APPLICATION_XML).build();
  }

  @GET
  @Path("/xml/{id: \\d+}")
  @Produces({MediaType.APPLICATION_XML})
  public Response getXml(@PathParam("id") int id) {
    checkContext();
    return toRequestedType(id, "application/xml");
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/json")
  public Response getJson() {
    checkContext();
    return Response.ok().entity(toJson(plist)).type(MediaType.APPLICATION_JSON).build();
  }

  @GET
  @Produces({MediaType.APPLICATION_JSON})
  @Path("/json/{id: \\d+}")
  public Response getJson(@PathParam("id") int id) {
    return toRequestedType(id, "application/json");
  }

  @GET
  @Path("/plain")
  @Produces({MediaType.TEXT_PLAIN})
  public String getPlain() {
    checkContext();
    return plist.toString();
  }

  @POST
  @Produces({MediaType.TEXT_PLAIN})
  @Path("/create")
  public Response create(@FormParam("who") String who, @FormParam("what") String what) {
    checkContext();
    String msg;
    if (who == null || what == null) {
      msg = "Property 'who' or 'what' is missing.\n";
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(msg)
          .type(MediaType.TEXT_PLAIN)
          .build();
    } else {
      int id = addPrediction(who, what);
      msg = "Prediction " + id + " created: (who = " + who + " what = " + what + ").\n";
      return Response.ok(msg, "text/plain").build();
    }
  }

  @PUT
  @Produces({MediaType.TEXT_PLAIN})
  @Path("/update")
  public Response update(
      @FormParam("id") int id, @FormParam("who") String who, @FormParam("what") String what) {
    checkContext();
    StringBuilder message = new StringBuilder();
    if (who == null && what == null) {
      message.append("Neither who nor what is given: nothing to edit.\n");
    }

    Prediction p = plist.find(id);
    if (p == null) {
      message.append("There is no prediction with ID " + id + "\n");
    }

    if (message.length() != 0) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(message)
          .type(MediaType.TEXT_PLAIN)
          .build();
    } else {
      if (who != null) {
        p.setWho(who);
      }

      if (what != null) {
        p.setWhat(what);
      }

      message.append("Prediction " + id + " has been updated.\n");
      return Response.ok(message, "text/plain").build();
    }
  }

  @DELETE
  @Produces({MediaType.TEXT_PLAIN})
  @Path("/delete/{id: \\d+}")
  public Response delete(@PathParam("id") int id) {
    checkContext();
    StringBuilder message = new StringBuilder();
    Prediction p = plist.find(id);
    if (p == null) {
      message.append("There is no prediction with ID " + id + ". Cannot delete.\n");
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(message)
          .type(MediaType.TEXT_PLAIN)
          .build();
    } else {
      plist.getPredictions().remove(p);
      message.append("Prediction " + id + " deleted.\n");
      return Response.ok(message, "text/plain").build();
    }
  }

  private void checkContext() {
    if (plist == null) {
      populate();
    }
  }

  private void populate() {
    plist = new PredictionsList();
    String filename = "/WEB-INF/classes/data/predictions.db";
    InputStream in = servletContext.getResourceAsStream(filename);

    if (in != null) {
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
        String record;
        while ((record = reader.readLine()) != null) {
          String[] parts = record.split("!");
          addPrediction(parts[0], parts[1]);
        }
      } catch (IOException e) {
        throw new RuntimeException("I/O failed!");
      }
    }
  }

  private int addPrediction(String who, String what) {
    int id = plist.add(who, what);
    return id;
  }

  // Prediction --> JSON document
  private String toJson(Prediction prediction) {
    String json = "If you see this, there's a problem.";
    try {
      json = new ObjectMapper().writeValueAsString(prediction);
    } catch (Exception e) {
    }
    return json;
  }

  // PredictionsList --> JSON document
  private String toJson(PredictionsList plist) {
    String json = "If you see this, there's a problem.";
    try {
      json = new ObjectMapper().writeValueAsString(plist);
    } catch (Exception e) {
    }
    return json;
  }

  // Generate an HTTP error response or typed OK response.
  private Response toRequestedType(int id, String type) {
    Prediction pred = plist.find(id);
    if (pred == null) {
      String msg = id + " is a bad ID.\n";
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(msg)
          .type(MediaType.TEXT_PLAIN)
          .build();
    } else if (type.contains("json")) {
      return Response.ok(toJson(pred), type).build();
    } else {
      return Response.ok(pred, type).build(); // toXml is automatic
    }
  }
}
