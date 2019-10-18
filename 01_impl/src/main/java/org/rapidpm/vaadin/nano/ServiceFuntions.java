package org.rapidpm.quarkus.vaadin;

import com.vaadin.flow.router.Route;
import org.rapidpm.dependencies.core.logger.HasLogger;

import java.util.Set;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableSet;

public interface ServiceFuntions
    extends HasLogger {

  String DEFAULT_PKG_TO_SCAN          = "org.rapidpm";
  String DEFAULT_PKG_TO_SCAN_PROPERTY = "vaadin-pkg-to-scan";

  static Supplier<Set<Class<?>>> routeClasses() {
    return () -> unmodifiableSet(new org.reflections8.Reflections(
        System.getProperty(DEFAULT_PKG_TO_SCAN_PROPERTY, DEFAULT_PKG_TO_SCAN)).getTypesAnnotatedWith(Route.class));
  }


}
