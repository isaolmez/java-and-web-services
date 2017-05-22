package com.isa.rest.service.restlet;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.ext.xml.DomRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlOneResource extends ServerResource {

  @Get
  public Representation toXml() {
    // Extract the adage's id.
    String sid = (String) getRequest().getAttributes().get("id");
    if (sid == null) return badRequest("No ID provided\n");

    int id;
    try {
      id = Integer.parseInt(sid.trim());
    } catch (Exception e) {
      return badRequest("No such ID\n");
    }

    // Search for the Adage.
    Adage adage = Adages.find(id);
    if (adage == null) return badRequest("No adage with ID " + id + "\n");

    // Generate the XML response.
    DomRepresentation dom = null;
    try {
      dom = new DomRepresentation(MediaType.TEXT_XML);
      dom.setIndenting(true);
      Document doc = dom.getDocument();

      Element root = doc.createElement("adage");
      root.appendChild(doc.createTextNode(adage.toString()));
      doc.appendChild(root);
    } catch (Exception e) {
      throw new RuntimeException("Error occurred when serializing", e);
    }

    return dom;
  }

  private StringRepresentation badRequest(String msg) {
    Status error = new Status(Status.CLIENT_ERROR_BAD_REQUEST, msg);
    return new StringRepresentation(error.toString());
  }
}
