package work.assisjrs;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by assis on 17/02/17.
 */
@Component
public class Router extends RouteBuilder {

    @Autowired
    private HealthEndpoint health;

    @Override
    public void configure() {
        from("timer:trigger")
                .transform().simple("ref:myBean")
                .to("log:out");

        from("timer:status")
                .bean(health, "invoke")
                .log("Health is ${body}");
    }

    @Bean
    String myBean() {
        return "I'm Spring bean!";
    }
}
