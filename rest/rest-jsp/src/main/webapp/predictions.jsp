<jsp:useBean
    id="preds"
    type="com.isa.rest.service.jsp.Predictions"
    class="com.isa.rest.service.jsp.Predictions">
	<%
		// Check the HTTP verb: if it's anything but GET,
			// return 405 (Method Not Allowed).
			String verb = request.getMethod();
			if (!verb.equalsIgnoreCase("GET")) {
				response.sendError(response.SC_METHOD_NOT_ALLOWED, "Only GET requests are allowed.");
			}
			// If it's a GET request, return the predictions.
			else {
				preds.setServletContext(application);
				out.println(verb);
				out.println(preds.getPredictionsAsXML());
			}
	%>
</jsp:useBean>