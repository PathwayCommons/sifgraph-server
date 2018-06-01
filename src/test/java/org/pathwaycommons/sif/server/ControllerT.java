package org.pathwaycommons.sif.server;

import static org.hamcrest.Matchers.containsString;
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

    @Test
    public void pathsbetween() {
        ResponseEntity<String> response = template.getForEntity("/pathsbetween?source=BMP2&source=NOG", String.class);
        assertThat(response.getBody(), containsString("NOG\tin-complex-with\tBMP2"));
    }

    @Test
    public void commonstream() {
        ResponseEntity<String> response = template.getForEntity("/commonstream?source=BMP2&source=BMPR1A", String.class);
        assertThat(response.getBody(), containsString("BMP2\tin-complex-with\tSMURF1"));
        assertThat(response.getBody(), containsString("BMP2\tcontrols-state-change-of\tBMPR1A"));
    }


    @Test
    public void pathsfromto() {
        ResponseEntity<String> response = template.getForEntity("/pathsfromto?source=BMP2&target=BMPR1A", String.class);
        System.out.println(response.getBody());
        assertThat(response.getBody(), containsString("BMP2\tcontrols-state-change-of\tBMPR1A"));
        assertThat(response.getBody(), containsString("BMP2\tin-complex-with\tBMPR1A"));
    }

}
