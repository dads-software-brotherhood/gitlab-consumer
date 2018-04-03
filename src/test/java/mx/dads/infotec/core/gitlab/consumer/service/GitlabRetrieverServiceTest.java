package mx.dads.infotec.core.gitlab.consumer.service;

import java.util.Map;
import mx.dads.infotec.core.gitlab.consumer.service.dto.GroupDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ListElementDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.junit.Before;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author erik.valdivieso
 */
@RunWith(SpringRunner.class)
@RestClientTest(GitlabRetrieverService.class)
public class GitlabRetrieverServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabRetrieverServiceTest.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GitlabRetrieverService gitlabRetrieverService;
    
    private MockRestServiceServer server;

    @Before
    public void setup() {

        server = MockRestServiceServer.createServer(restTemplate);

        server.verify();

        this.server.expect(requestTo("http://localhost" + GitlabRetrieverService.GROUPS))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getGroupsTest() {
        ListElementDTO<GroupDTO> tmp = this.gitlabRetrieverService.getGroups();

        //assert tmp.getList().size() == 5 : "Expect 5 elements";
        assert tmp.getPageInfoDTO() != null : "Expect page info data";

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(tmp.getPageInfoDTO().toString());

            LOGGER.debug("Lista:");

            tmp.getList().forEach((groupDTO) -> LOGGER.debug(groupDTO.toString()));
        }
    }

}
