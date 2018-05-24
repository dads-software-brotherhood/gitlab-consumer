package mx.dads.infotec.core.gitlab.consumer.service;

import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.GROUPS;
import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.GROUP_PROJECTS;
import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.PROJECT_COMMITS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.text.MessageFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import mx.dads.infotec.core.gitlab.consumer.service.dto.CommitDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.GroupDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ListElementDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.PageInfoDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ProjectDTO;
import mx.dads.infotec.core.gitlab.consumer.util.DateUtils;
import mx.dads.infotec.core.gitlab.consumer.util.TextUtils;

/**
 * GitlabRetrieverService Test.
 *
 * @author erik.valdivieso
 */
@RunWith(SpringRunner.class)
@RestClientTest(GitlabRetrieverService.class)
public class GitlabRetrieverServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabRetrieverServiceTest.class);

    private static final String JSON_LIST = "[]";
    private static final String URL = "http://localhost";
    private static final Integer ID_GROUP = 1;
    private static final Integer ID_PROJECT = 22;

    private static MessageFormat getProjectsUrlFormat = new MessageFormat(URL + GROUP_PROJECTS);
    private static MessageFormat getProjectsCommitsUrlFormat = new MessageFormat(URL + PROJECT_COMMITS);

    @Autowired
    private GitlabRetrieverService gitlabRetrieverService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ResourceLoader resourceLoader;

    private String groupsContent;
    private String groupProjectsContent;
    private String projectCommitsContent;

    private HttpHeaders groupsHeaders;

    @Before
    public void setup() {
        groupsContent = loadContent("groups.json");
        groupProjectsContent = loadContent("group-projects.json");
        projectCommitsContent = loadContent("project-commits.json");

        groupsHeaders = new HttpHeaders();
        groupsHeaders.add("X-Next-Page", "3");
        groupsHeaders.add("X-Page", "2");
        groupsHeaders.add("X-Per-Page", "5");
        groupsHeaders.add("X-Prev-Page", "1");
        groupsHeaders.add("X-Total", "40");
        groupsHeaders.add("X-Total-Pages", "8");
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
        groupsTest(null);
    }

    @Test
    public void getGroupsParamTest() {
        groupsTest(new PageInfoDTO(2, 5));
    }

    private void groupsTest(PageInfoDTO pageInfoDTO) throws AssertionError {
        ListElementDTO<GroupDTO> groupsDto;
        String urlPart = URL + GROUPS;

        if (pageInfoDTO == null) {
            this.server.expect(requestTo(urlPart))
                    .andRespond(withSuccess(groupsContent, MediaType.APPLICATION_JSON_UTF8).headers(groupsHeaders));
            groupsDto = this.gitlabRetrieverService.getGroups();
        } else {
            this.server
                    .expect(requestTo(
                            urlPart + "?page=" + pageInfoDTO.getPage() + "&per_page=" + pageInfoDTO.getPerPage()))
                    .andRespond(withSuccess(groupsContent, MediaType.APPLICATION_JSON_UTF8).headers(groupsHeaders));
            groupsDto = this.gitlabRetrieverService.getGroups(pageInfoDTO);
        }

        assert groupsDto != null && groupsDto.getList() != null : "Return is invalid";
        assert groupsDto.getList().size() == 5 : "Expect 5 elements";
        assert groupsDto.getPageInfoDTO() != null : "Expect page info data";
        assert groupsDto.getPageInfoDTO().getTotal() != null
                && groupsDto.getPageInfoDTO().getTotal() == 40 : "40 Elements as total info";

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(groupsDto.getPageInfoDTO().toString());

            LOGGER.debug("Groups list:");

            groupsDto.getList().forEach((groupDTO) -> LOGGER.debug(groupDTO.toString()));
        }

        GroupDTO groupDTO = groupsDto.getList().get(0);

        assert groupDTO.getId() != null && groupDTO.getId() == 7 : "Expect ID 7 in firts element";
        assert groupDTO.getName() != null
                && groupDTO.getName().equals("arquitectura-old") : "Expect 'arquitectura-old' as name";
        assert groupDTO.getVisibility() != null
                && groupDTO.getVisibility().equals("public") : "Expect 'public' as visibility";
        assert groupDTO.getWebUrl() != null && groupDTO.getWebUrl().equals(
                "http://gitlab.dads.infotec.mx/groups/arquitectura-old") : "Expect 'http://gitlab.dads.infotec.mx/groups/arquitectura-old' in webUrl";
    }

    @Test
    public void getProjectGroupsTest() {
        projectGroupsTest(null);
    }

    @Test
    public void getProjectGroupsParamTest() {
        projectGroupsTest(new PageInfoDTO(2, 5));
    }

    private void projectGroupsTest(PageInfoDTO pageInfoDTO) throws AssertionError {
        ListElementDTO<ProjectDTO> projects;
        String urlPart = getProjectsUrlFormat.format(new Object[] { ID_GROUP });

        if (pageInfoDTO == null) {
            this.server.expect(requestTo(urlPart))
                    .andRespond(withSuccess(groupProjectsContent, MediaType.APPLICATION_JSON_UTF8));

            projects = this.gitlabRetrieverService.getProjects(ID_GROUP);
        } else {
            this.server
                    .expect(requestTo(
                            urlPart + "?page=" + pageInfoDTO.getPage() + "&per_page=" + pageInfoDTO.getPerPage()))
                    .andRespond(
                            withSuccess(groupProjectsContent, MediaType.APPLICATION_JSON_UTF8).headers(groupsHeaders));
            projects = this.gitlabRetrieverService.getProjects(ID_GROUP, pageInfoDTO);
        }

        assert projects != null && projects.getList() != null : "Is invalid";

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Projects list:");
            projects.getList().forEach((projectDTO) -> LOGGER.debug(projectDTO.toString()));
        }

        ProjectDTO projectDTO = projects.getList().get(0);

        assert projectDTO.getId() != null && projectDTO.getId() == 227 : "Expect 227 as ID";
        assert projectDTO.getName() != null
                && projectDTO.getName().equals("Conacyt-Catalogos") : "Expect 'Conacyt-Catalogos' as Name";
        assert projectDTO.getDefaultBranch() != null
                && projectDTO.getDefaultBranch().equals("master") : "Expect master as Default Branch";
        assert projectDTO.getLinks() != null : "Except links not null";
        assert projectDTO.getLinks().getSelf() != null
                && projectDTO.getLinks().getSelf().equals("http://gitlab.dads.infotec.mx/api/v4/projects/227");
        assert projectDTO.getNamespace() != null : "Except namespace not null";
        assert projectDTO.getNamespace().getFullPath() != null
                && projectDTO.getNamespace().getFullPath().equals("repositorio-nacional");
    }

    @Test
    public void getCommitsTest() {
        commitsTest(null);
    }

    @Test
    public void getCommitsParamTest() {
        commitsTest(new PageInfoDTO(2, 5));
    }

    private void commitsTest(PageInfoDTO pageInfoDTO) throws AssertionError {
        ListElementDTO<CommitDTO> commitsDto;
        String urlPart = getProjectsCommitsUrlFormat.format(new Object[] { ID_PROJECT });

        if (pageInfoDTO == null) {
            this.server.expect(requestTo(urlPart))
            .andRespond(withSuccess(projectCommitsContent, MediaType.APPLICATION_JSON_UTF8).headers(groupsHeaders));

            commitsDto = this.gitlabRetrieverService.getCommits(ID_PROJECT);
        } else {
            this.server.expect(requestTo(urlPart + "?page=" + pageInfoDTO.getPage() + "&per_page=" + pageInfoDTO.getPerPage()))
            .andRespond(withSuccess(projectCommitsContent, MediaType.APPLICATION_JSON_UTF8).headers(groupsHeaders));

            commitsDto = this.gitlabRetrieverService.getCommits(ID_PROJECT, pageInfoDTO);
        }

        assert commitsDto != null && commitsDto.getList() != null : "Return is invalid";
        assert commitsDto.getPageInfoDTO() != null : "Expect page info data";
        assert commitsDto.getPageInfoDTO().getTotal() != null : "Total info";

        CommitDTO commitDTO = commitsDto.getList().get(0);

        assertEquals("Expecto 'a6ef8ace9fc7adc8886d6f15c91a685fb61ad8a6' as ID", "a6ef8ace9fc7adc8886d6f15c91a685fb61ad8a6", commitDTO.getId());
        assertEquals("a6ef8ace", commitDTO.getShortId());
        assertEquals("Merge branch 'QA' into 'develop'", commitDTO.getTitle());
        assertNotNull(commitDTO.getParentIds());
        assertEquals(DateUtils.toDate("2018-04-02T14:27:05.000-05:00"), commitDTO.getCreatedAt());
        assertEquals("Merge branch 'QA' into 'develop'\n\nQA to develop\n\nSee merge request repositorio-nacional/repositorio-institucional!19", commitDTO.getMessage());
        assertEquals("Victor Daniel Gutierrez Rodriguez", commitDTO.getAuthorName());
        assertEquals("vdaniel.gr@gmail.com", commitDTO.getAuthorEmail());
        assertEquals(DateUtils.toDate("2018-04-02T14:27:05.000-05:00"), commitDTO.getAuthoredDate());
        assertEquals("Victor Daniel Gutierrez Rodriguez", commitDTO.getCommitterName());
        assertEquals("vdaniel.gr@gmail.com", commitDTO.getCommitterEmail());
        assertEquals(DateUtils.toDate("2018-04-02T14:27:05.000-05:00"), commitDTO.getCommittedDate());
    }

}
