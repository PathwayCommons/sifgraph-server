package org.pathwaycommons.sif.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(SifgraphProperties.class)
public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        ((Controller)ctx.getBean("controller")).init();
    }

}

