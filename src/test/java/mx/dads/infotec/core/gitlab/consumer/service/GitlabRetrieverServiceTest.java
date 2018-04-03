package mx.dads.infotec.core.gitlab.consumer.service;

import mx.dads.infotec.core.gitlab.consumer.service.dto.GroupDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ListElementDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ProjectDTO;
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
    private static final String URL = "http://localhost";
    private static final int ID_GROUP = 1;

    @Autowired
    private GitlabRetrieverService gitlabRetrieverService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ResourceLoader resourceLoader;

    private String contentGroups;
    private String contentProjectsGroup;

    @Before
    public void setup() {
        contentGroups = loadContent("groups.json");
        contentProjectsGroup = loadContent("projects-group.json");
    }

    private String loadContent(String file) {
        Resource resource = resourceLoader.getResource("classpath:static/" + file);

        if (resource.exists()) {
            return TextUtils.readText(resource, JSON_LIST);
        } else {
            return JSON_LIST;
        }
    }

    @Test
    public void getGroupsTest() {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Next-Page", "3");
        headers.add("X-Page", "2");
        headers.add("X-Per-Page", "5");
        headers.add("X-Prev-Page", "1");
        headers.add("X-Total", "40");
        headers.add("X-Total-Pages", "8");

        this.server.expect(requestTo(URL + GitlabRetrieverService.GROUPS))
                .andRespond(withSuccess(contentGroups, MediaType.APPLICATION_JSON_UTF8).headers(headers));

        ListElementDTO<GroupDTO> tmp = this.gitlabRetrieverService.getGroups();

        assert tmp != null && tmp.getList() != null : "Return is invalid";
        assert tmp.getList().size() == 5 : "Expect 5 elements";
        assert tmp.getPageInfoDTO() != null : "Expect page info data";
        assert tmp.getPageInfoDTO().getTotal() != null : "Total info";

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(tmp.getPageInfoDTO().toString());

            LOGGER.debug("Groups list:");

            tmp.getList().forEach((groupDTO) -> LOGGER.debug(groupDTO.toString()));
        }
        
        assert tmp.getList().get(0).getId() != null && tmp.getList().get(0).getId() == 7 : "Expect ID 7 in firts element";
        assert tmp.getList().get(0).getName() != null && tmp.getList().get(0).getName().equals("arquitectura-old") : "Expect 'arquitectura-old' as name";
        assert tmp.getList().get(0).getVisibility() != null && tmp.getList().get(0).getVisibility().equals("public") : "Expect 'public' as visibility";
        assert tmp.getList().get(0).getWebUrl() != null && tmp.getList().get(0).getWebUrl().equals("http://gitlab.dads.infotec.mx/groups/arquitectura-old") : "Expect 'http://gitlab.dads.infotec.mx/groups/arquitectura-old' in webUrl";
    }

    @Test
    public void getProjectGroupsTest() {
        this.server.expect(requestTo(URL + GitlabRetrieverService.PROJECTS_GROUP_PART1 + ID_GROUP + GitlabRetrieverService.PROJECTS_GROUP_PART2))
                .andRespond(withSuccess(contentProjectsGroup, MediaType.APPLICATION_JSON_UTF8));

        ListElementDTO<ProjectDTO> projects = this.gitlabRetrieverService.getProjects(ID_GROUP);

        assert projects != null && projects.getList() != null : "Is invalid";

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Projects list:");
            projects.getList().forEach((projectDTO) -> LOGGER.debug(projectDTO.toString()));
        }
    }

}
