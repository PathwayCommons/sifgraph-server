package org.pathwaycommons.sif.server;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "sifgraph.data=classpath:bmp.gz",
    "sifgraph.annotations=DATA_SOURCE,PUBMED_IDS,PATHWAY_NAMES,MEDIATORS",
    "sifgraph.relationships=CONTROLS_STATE_CHANGE_OF,IN_COMPLEX_WITH"
})
public class ControllerT {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void neighborhood() {
        ResponseEntity<String> response = template.getForEntity("/neighborhood?source=bmp2", String.class);
        assertThat(response.getBody(), equalTo(null)); //no result because gene symbols are case-sensitive!
        response = template.getForEntity("/neighborhood?source=BMP2", String.class);
        assertThat(response.getBody(), containsString("BMP2\tcontrols-state-change-of\tBMPR1A"));
    }

}
