package hello;

import org.apache.camel.Exchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
                new CamelHttpTransportServlet(), "/camel-rest-jpa/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
            restConfiguration()
                    .contextPath("/camel-rest-jpa").apiContextPath("/api-doc")
                    .apiProperty("api.title", "Camel REST API")
                    .apiProperty("api.version", "1.0")
                    .apiProperty("cors", "true")
                    .apiContextRouteId("doc-api")
                    .bindingMode(RestBindingMode.json);

            rest("/books").description("Books REST service")
                    .get("/{id}").description("The list of all the books")
                    .route().routeId("books-api")
                    .log("${header.id}")
                    .setHeader(Exchange.HTTP_QUERY,simple("name=${header.id}"))
                    .enrich("http4://localhost:8080/some/booking/")
                    .convertBodyTo(String.class)
                    .log("${body}")
                    .bean(Database.class, "findAll")
                    .endRest();
        }
    }
}
