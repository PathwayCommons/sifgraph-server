package org.pathwaycommons.sif.server;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@Import(Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerT {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getHello() {
        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody(), equalTo("Hello!"));
    }

    @Test
    public void getSif() {
        ResponseEntity<String> response = template
            .getForEntity("/sifgraph?kind=neighborhood&source=foo", String.class);
        assertThat(response.getBody(), equalTo(null));
    }

}
