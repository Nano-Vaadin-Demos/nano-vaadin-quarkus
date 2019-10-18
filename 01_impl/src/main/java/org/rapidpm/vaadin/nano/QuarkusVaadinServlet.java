package org.rapidpm.quarkus.vaadin;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.startup.ApplicationRouteRegistry;
import org.rapidpm.dependencies.core.logger.HasLogger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

import static org.rapidpm.quarkus.vaadin.ServiceFuntions.routeClasses;

public class QuarkusVaadinServlet
    extends VaadinServlet
    implements HasLogger {

  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
    ServletContext           servletContext     = servletConfig.getServletContext();
    ApplicationRouteRegistry routeRegistry      = ApplicationRouteRegistry.getInstance(servletContext);
    RouteConfiguration       routeConfiguration = RouteConfiguration.forRegistry(routeRegistry);
    setOfRouteAnnotatedClasses().stream()
                                .filter(Component.class::isAssignableFrom)
                                .forEach(aClass -> {
                                  routeConfiguration.setAnnotatedRoute((Class<Component>) aClass);
                                });
    super.init(servletConfig);
  }

  /**
   * it is public to give tha chance to override it with a defined static list of Views
   *
   * @return a set of classes annotated with Route
   */
  public Set<Class<?>> setOfRouteAnnotatedClasses() {
    return routeClasses().get();
  }

}
