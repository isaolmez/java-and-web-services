package com.isa.rest.service.jaxrs.adage;

import com.isa.rest.service.jaxrs.adage.resource.AdageAutomaticConversionResource;
import com.isa.rest.service.jaxrs.adage.resource.AdageManualConversionResource;
import com.isa.rest.service.jaxrs.adage.resource.AdageMultipleRepresentationResource;
import com.isa.rest.service.jaxrs.adage.resource.AdageMultipleRepresentationV2Resource;
import org.glassfish.jersey.logging.LoggingFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.glassfish.jersey.logging.LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER;
import static org.glassfish.jersey.logging.LoggingFeature.LOGGING_FEATURE_VERBOSITY_SERVER;

@ApplicationPath("/adage")
public class AdageApplication extends Application {
  @Override
  public Set<Class<?>> getClasses() {
    final Set<Class<?>> set = new HashSet<>();
    set.add(AdageManualConversionResource.class);
    set.add(AdageAutomaticConversionResource.class);
    set.add(AdageMultipleRepresentationResource.class);
    set.add(AdageMultipleRepresentationV2Resource.class);
    set.add(LoggingFeature.class);
    return set;
  }

  @Override
  public Map<String, Object> getProperties() {
    final Map<String, Object> properties = new HashMap<>();
    properties.put(LOGGING_FEATURE_VERBOSITY_SERVER, LoggingFeature.Verbosity.PAYLOAD_ANY);
    properties.put(LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "INFO");
    return properties;
  }
}
