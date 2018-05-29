package org.pathwaycommons.sif.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    public Controller() {
    }

    @RequestMapping("/")
    public String index() {
        return "Hello!";
    }

    @RequestMapping("/sifgraph")
    public String sifgraph() {
    return null;
  }

}
