package mx.dads.infotec.core.gitlab.consumer.service;

import mx.dads.infotec.core.gitlab.consumer.service.dto.GroupDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ListElementDTO;
import mx.dads.infotec.core.gitlab.consumer.util.TextUtils;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 *
 * @author erik.valdivieso
 */
@RunWith(SpringRunner.class)
@RestClientTest(GitlabRetrieverService.class)
public class GitlabRetrieverServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabRetrieverServiceTest.class);

    private static final String JSON_LIST = "[]";
    
    @Autowired
    private GitlabRetrieverService gitlabRetrieverService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ResourceLoader resourceLoader;

    @Before
    public void setup() {

        Resource resource = resourceLoader.getResource("classpath:static/groups.json");

        String res;

        if (resource.exists()) {
            res = TextUtils.readText(resource, JSON_LIST);
        } else {
            res = JSON_LIST;
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Next-Page", "3");
        headers.add("X-Page", "2");
        headers.add("X-Per-Page", "5");
        headers.add("X-Prev-Page", "1");
        headers.add("X-Total", "40");
        headers.add("X-Total-Pages", "8");

        this.server.expect(requestTo("http://localhost" + GitlabRetrieverService.GROUPS))
                .andRespond(withSuccess(res, MediaType.APPLICATION_JSON_UTF8).headers(headers));
    }

    @Test
    public void getGroupsTest() {
        ListElementDTO<GroupDTO> tmp = this.gitlabRetrieverService.getGroups();

        assert tmp.getList().size() == 5 : "Expect 5 elements";
        assert tmp.getPageInfoDTO() != null : "Expect page info data";
        assert tmp.getPageInfoDTO().getTotal() != null : "Total info";

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(tmp.getPageInfoDTO().toString());

            LOGGER.debug("Lista:");

            tmp.getList().forEach((groupDTO) -> LOGGER.debug(groupDTO.toString()));
        }
    }

}
