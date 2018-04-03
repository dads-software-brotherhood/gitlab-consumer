package mx.dads.infotec.core.gitlab.consumer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
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

    @Autowired
    private GitlabRetrieverService gitlabRetrieverService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ResourceLoader resourceLoader;

    @Before
    public void setup() {

        Resource resource = resourceLoader.getResource("classpath:static/groups.json");

        String res = null;

        if (resource.exists()) {
            res = read(resource);
        }
        
        if (res == null) {
            res = "[]";
        }

        this.server.expect(requestTo("http://localhost" + GitlabRetrieverService.GROUPS))
                .andRespond(withSuccess(res, MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getGroupsTest() {
        ListElementDTO<GroupDTO> tmp = this.gitlabRetrieverService.getGroups();

        assert tmp.getList().size() == 5 : "Expect 5 elements";
        assert tmp.getPageInfoDTO() != null : "Expect page info data";

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(tmp.getPageInfoDTO().toString());

            LOGGER.debug("Lista:");

            tmp.getList().forEach((groupDTO) -> LOGGER.debug(groupDTO.toString()));
        }
    }

    private String read(Resource resource) {

        try (InputStream is = resource.getInputStream()) {
            StringBuilder sb = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            return sb.toString();
        } catch (IOException ex) {
            LOGGER.error("Error at read", ex);
        }

        return null;
    }

}
