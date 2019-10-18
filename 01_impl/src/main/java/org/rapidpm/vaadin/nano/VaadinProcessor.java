package org.rapidpm.quarkus.vaadin;

import com.vaadin.flow.router.Route;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.BeanDefiningAnnotationBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.substrate.ReflectiveClassBuildItem;
import io.quarkus.undertow.deployment.ServletBuildItem;
import org.jboss.jandex.DotName;
import org.rapidpm.dependencies.core.logger.HasLogger;

import static org.rapidpm.quarkus.vaadin.ServiceFuntions.routeClasses;

public class VaadinProcessor
    implements HasLogger {

  private static DotName routeAnnotation = DotName.createSimple(Route.class.getName());

  @BuildStep
  FeatureBuildItem featureBuildItem() {
    logger().info("Add Feature For Vaadin");
    return new FeatureBuildItem("quarkus-vaadin");
  }

  @BuildStep
  BeanDefiningAnnotationBuildItem registerRouteAnnotation() {
    logger().info("Add Feature For Vaadin - registerRouteAnnotation");
    return new BeanDefiningAnnotationBuildItem(routeAnnotation);
  }

  @BuildStep
  AdditionalBeanBuildItem registerVaadinServlet() {
    logger().info("Add Feature For Vaadin - registerVaadinServlet");
    //TODO make it dynamic
    return new AdditionalBeanBuildItem(QuarkusVaadinServlet.class);
  }

  @BuildStep
  ServletBuildItem vaadinServletBuildItem() {
    logger().info("Add Feature For Vaadin - vaadinServletBuildItem");
    //TODO make it dynamic
    return ServletBuildItem.builder(QuarkusVaadinServlet.class.getSimpleName(), QuarkusVaadinServlet.class.getName())
                           .addMapping("/vaadin/*")
                           .addMapping("/frontend/*")
                           .build();
  }

  @BuildStep
  void scanForBeans(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
    logger().info("Add Feature For Vaadin - scanForBeans");
    routeClasses().get()
                  .stream()
                  .map(c -> c.getAnnotation(Route.class))
                  .map(Route::value)
                  .peek(v -> logger().info("add value (Route) - " + v))
                  .forEach(value -> reflectiveClass.produce(new ReflectiveClassBuildItem(false, false, value)));

  }


  //todo replace with reflections8
  @BuildStep
  void reflection(BuildProducer<ReflectiveClassBuildItem> reflectiveClass) {
    logger().info("Register reflective CLasses");

    // Vaadin
    ReflectiveClassBuildItem vaadinClassBuildItem = ReflectiveClassBuildItem.builder("com.vaadin.flow.component.UI",
                                                                                     "com.vaadin.flow.component.PollEvent",
                                                                                     "com.vaadin.flow.component.ClickEvent",
                                                                                     "com.vaadin.flow.component.CompositionEndEvent",
                                                                                     "com.vaadin.flow.component.CompositionStartEvent",
                                                                                     "com.vaadin.flow.component.CompositionUpdateEvent",
                                                                                     "com.vaadin.flow.component.KeyDownEvent",
                                                                                     "com.vaadin.flow.component.KeyPressEvent",
                                                                                     "com.vaadin.flow.component.KeyUpEvent",
                                                                                     "com.vaadin.flow.component.splitlayout.GeneratedVaadinSplitLayout$SplitterDragendEvent",
                                                                                     "com.vaadin.flow.component.details.Details$OpenedChangeEvent",
                                                                                     "com.vaadin.flow.component.details.Details",
                                                                                     "com.vaadin.flow.router.InternalServerError",
                                                                                     "com.vaadin.flow.router.RouteNotFoundError",
                                                                                     "com.vaadin.flow.theme.lumo.Lumo")
                                                                            .constructors(true)
                                                                            .methods(true)
                                                                            .build();

    reflectiveClass.produce(vaadinClassBuildItem);
    // Athmosphere
    ReflectiveClassBuildItem athmosClassBuildItem = ReflectiveClassBuildItem.builder(
        "org.atmosphere.cpr.DefaultBroadcaster", "org.atmosphere.cpr.DefaultAtmosphereResourceFactory",
        "org.atmosphere.cpr.DefaultBroadcasterFactory", "org.atmosphere.cpr.DefaultMetaBroadcaster",
        "org.atmosphere.cpr.DefaultAtmosphereResourceSessionFactory", "org.atmosphere.util.VoidAnnotationProcessor",
        "org.atmosphere.cache.UUIDBroadcasterCache", "org.atmosphere.websocket.protocol.SimpleHttpProtocol",
        "org.atmosphere.interceptor.IdleResourceInterceptor", "org.atmosphere.interceptor.OnDisconnectInterceptor",
        "org.atmosphere.interceptor.WebSocketMessageSuspendInterceptor",
        "org.atmosphere.interceptor.JavaScriptProtocol", "org.atmosphere.interceptor.JSONPAtmosphereInterceptor",
        "org.atmosphere.interceptor.SSEAtmosphereInterceptor",
        "org.atmosphere.interceptor.AndroidAtmosphereInterceptor",
        "org.atmosphere.interceptor.PaddingAtmosphereInterceptor", "org.atmosphere.interceptor.CacheHeadersInterceptor",
        "org.atmosphere.interceptor.CorsInterceptor")
                                                                            .constructors(true)
                                                                            .methods(true)
                                                                            .build();

    reflectiveClass.produce(athmosClassBuildItem);
  }


}
