package com.isa.rest.service.jaxrs.prediction;

import com.isa.rest.service.jaxrs.prediction.resource.PredictionResource;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.glassfish.jersey.logging.LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER;
import static org.glassfish.jersey.logging.LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER;

@ApplicationPath("/prediction")
public class PredictionApplication extends Application {
  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> set = new HashSet<Class<?>>();
    set.add(PredictionResource.class);
    set.add(LoggingFeature.class);
    return set;
  }

  @Override
  public Map<String, Object> getProperties() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put(LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
    properties.put(LOGGING_FEATURE_VERBOSITY_SERVER, LoggingFeature.Verbosity.PAYLOAD_ANY);
    return properties;
  }
}
