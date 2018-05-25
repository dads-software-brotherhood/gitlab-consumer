package mx.dads.infotec.core.gitlab.consumer.service;

import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.GROUPS;
import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.GROUP_PROJECTS;
import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.PROJECT_COMMITS;
import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.PROJECT_SINGLE_COMMIT;
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

    private static final String EMPTY_JSON_LIST = "[]";
    private static final String EMPTY_JSON = "{}";
    private static final String URL = "http://localhost";
    private static final Integer ID_GROUP = 7;
    private static final Integer ID_PROJECT = 227;
    private static final String COMMIT_HASH = "bad67193a377c1244bbb014595983621a2a7a09a";

    private static final int PAGE = 2;
    private static final int PER_PAGE = 5;
    private static final int TOTAL_GROUPS = 40;

    private static MessageFormat getProjectsUrlFormat = new MessageFormat(URL + GROUP_PROJECTS);
    private static MessageFormat getProjectsCommitsUrlFormat = new MessageFormat(URL + PROJECT_COMMITS);
    private static MessageFormat getProjectSingleCommitUrlFormat = new MessageFormat(URL + PROJECT_SINGLE_COMMIT);

    @Autowired
    private GitlabRetrieverService gitlabRetrieverService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ResourceLoader resourceLoader;

    private String groupsContent;
    private String groupProjectsContent;
    private String projectCommitsContent;
    private String projectSingleCommitContent;

    private HttpHeaders groupsHeaders;

    @Before
    public void setup() {
        groupsContent = loadListContent("groups.json");
        groupProjectsContent = loadListContent("group-projects.json");
        projectCommitsContent = loadListContent("project-commits.json");
        projectSingleCommitContent = loadSingleContent("project-single-commit.json");

        groupsHeaders = new HttpHeaders();
        groupsHeaders.add("X-Next-Page", "3");
        groupsHeaders.add("X-Page", PAGE + "");
        groupsHeaders.add("X-Per-Page", PER_PAGE + "");
        groupsHeaders.add("X-Prev-Page", "1");
        groupsHeaders.add("X-Total", TOTAL_GROUPS + "");
        groupsHeaders.add("X-Total-Pages", "8");
    }

    private String loadListContent(String file) {
        return loadContent(file, EMPTY_JSON_LIST);
    }

    private String loadSingleContent(String file) {
        return loadContent(file, EMPTY_JSON);
    }

    private String loadContent(String file, String defaultValue) {
        Resource resource = resourceLoader.getResource("classpath:static/" + file);

        if (resource.exists()) {
            return TextUtils.readText(resource, defaultValue);
        } else {
            return defaultValue;
        }
    }
    
    @Test
    public void getGroupsTest() {
        groupsTest(null);
    }

    @Test
    public void getGroupsParamTest() {
        groupsTest(new PageInfoDTO(PAGE, PER_PAGE));
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

        assertNotNull(groupsDto);
        assertNotNull(groupsDto.getList());
        assertEquals(PER_PAGE, groupsDto.getList().size());
        assertNotNull(groupsDto.getPageInfoDTO());
        assertEquals(new Integer(TOTAL_GROUPS), groupsDto.getPageInfoDTO().getTotal());

        GroupDTO groupDTO = groupsDto.getList().get(0);

        String commonText = "arquitectura-old";

        assertEquals(ID_GROUP, groupDTO.getId());
        assertEquals(commonText, groupDTO.getName());
        assertEquals(commonText, groupDTO.getPath());
        assertEquals("Grupo de la antigua torre/coordinación de arquitectura", groupDTO.getDescription());
        assertEquals("public", groupDTO.getVisibility());
        assertEquals(Boolean.TRUE, groupDTO.getLfsEnabled());
        assertEquals("http://gitlab.dads.infotec.mx/groups/arquitectura-old/avatar", groupDTO.getAvatarUrl());
        assertEquals("http://gitlab.dads.infotec.mx/groups/arquitectura-old", groupDTO.getWebUrl());
        assertEquals(Boolean.TRUE, groupDTO.getRequestAccessEnabled());
        assertEquals(commonText, groupDTO.getFullName());
        assertEquals(commonText, groupDTO.getFullPath());
        assertEquals(new Integer(1), groupDTO.getParentId());
    }

    @Test
    public void getProjectGroupsTest() {
        projectGroupsTest(null);
    }

    @Test
    public void getProjectGroupsParamTest() {
        projectGroupsTest(new PageInfoDTO(PAGE, PER_PAGE));
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

        assertNotNull(projects);
        assertNotNull(projects.getList());

        ProjectDTO projectDTO = projects.getList().get(0);

        assertEquals(ID_PROJECT, projectDTO.getId());
        assertEquals("Proyecto para catálogos", projectDTO.getDescription());
        assertEquals("Conacyt-Catalogos", projectDTO.getName());
        assertEquals("repositorio-nacional / Conacyt-Catalogos", projectDTO.getNameWithNamespace());
        assertEquals("Conacyt-Catalogos", projectDTO.getPath());
        assertEquals("repositorio-nacional/Conacyt-Catalogos", projectDTO.getPathWithNamespace());
        assertEquals(DateUtils.toDate("2018-04-03T21:17:33.630Z"), projectDTO.getCreatedAt());
        assertEquals("master", projectDTO.getDefaultBranch());
        assertEquals("git@gitlab.dads.infotec.mx:repositorio-nacional/Conacyt-Catalogos.git", projectDTO.getSshUrlToRepo());
        assertEquals("http://gitlab.dads.infotec.mx/repositorio-nacional/Conacyt-Catalogos.git", projectDTO.getHttpUrlToRepo());
        assertEquals("http://gitlab.dads.infotec.mx/repositorio-nacional/Conacyt-Catalogos", projectDTO.getWebUrl());
        assertEquals("http://gitlab.dads.infotec.mx/repositorio-nacional/Conacyt-Catalogos/avatar", projectDTO.getAvatarUrl());
        assertEquals(new Integer(10), projectDTO.getStarCount());        
        assertEquals(new Integer(15), projectDTO.getForksCount());        
        assertEquals(DateUtils.toDate("2018-04-03T21:17:33.630Z"), projectDTO.getLastActivityAt());
        assertNotNull(projectDTO.getLinks());
        assertEquals("http://gitlab.dads.infotec.mx/api/v4/projects/227", projectDTO.getLinks().getSelf());
        assertEquals("http://gitlab.dads.infotec.mx/api/v4/projects/227/issues", projectDTO.getLinks().getIssues());
        assertEquals("http://gitlab.dads.infotec.mx/api/v4/projects/227/merge_requests", projectDTO.getLinks().getMergeRequests());
        assertEquals("http://gitlab.dads.infotec.mx/api/v4/projects/227/repository/branches", projectDTO.getLinks().getRepoBranches());
        assertEquals("http://gitlab.dads.infotec.mx/api/v4/projects/227/labels", projectDTO.getLinks().getLabels());
        assertEquals("http://gitlab.dads.infotec.mx/api/v4/projects/227/events", projectDTO.getLinks().getEvents());
        assertEquals("http://gitlab.dads.infotec.mx/api/v4/projects/227/members", projectDTO.getLinks().getMembers());
        assertEquals(Boolean.FALSE, projectDTO.getArchived());
        assertEquals("private", projectDTO.getVisibility());
        assertEquals(Boolean.FALSE, projectDTO.getRequestAccessEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getContainerRegistryEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getIssuesEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getMergeRequestsEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getWikiEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getJobsEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getSnippetsEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getSharedRunnersEnabled());
        assertEquals(Boolean.TRUE, projectDTO.getLfsEnabled());
        assertEquals(new Integer(179), projectDTO.getCreatorId());
        assertNotNull(projectDTO.getNamespace());
        assertEquals(new Integer(25), projectDTO.getNamespace().getId());
        assertEquals("repositorio-nacional", projectDTO.getNamespace().getName());
        assertEquals("repositorio-nacional", projectDTO.getNamespace().getPath());
        assertEquals("group", projectDTO.getNamespace().getKind());
        assertEquals("repositorio-nacional", projectDTO.getNamespace().getFullPath());
        assertEquals(new Integer(51), projectDTO.getNamespace().getParentId());
        assertEquals("finished", projectDTO.getImportStatus());
        assertEquals(new Integer(0), projectDTO.getOpenIssuesCount());
        assertEquals(Boolean.TRUE, projectDTO.getPublicJobs());
        assertEquals(Boolean.FALSE, projectDTO.getOnlyAllowMergeIfPipelineSucceeds());
        assertEquals(Boolean.FALSE, projectDTO.getRequestAccessEnabled());
        assertEquals(Boolean.FALSE, projectDTO.getOnlyAllowMergeIfAllDiscussionsAreResolved());
        assertEquals(Boolean.TRUE, projectDTO.getPrintingMergeRequestLinkEnabled());
    }

    @Test
    public void getCommitsTest() {
        commitsTest(null);
    }

    @Test
    public void getCommitsParamTest() {
        commitsTest(new PageInfoDTO(PAGE, PER_PAGE));
    }

    private void commitsTest(PageInfoDTO pageInfoDTO) throws AssertionError {
        ListElementDTO<CommitDTO> commitsDto;
        String urlPart = getProjectsCommitsUrlFormat.format(new Object[] { ID_PROJECT });

        if (pageInfoDTO == null) {
            this.server.expect(requestTo(urlPart)).andRespond(
                    withSuccess(projectCommitsContent, MediaType.APPLICATION_JSON_UTF8).headers(groupsHeaders));

            commitsDto = this.gitlabRetrieverService.getCommits(ID_PROJECT);
        } else {
            this.server
                    .expect(requestTo(
                            urlPart + "?page=" + pageInfoDTO.getPage() + "&per_page=" + pageInfoDTO.getPerPage()))
                    .andRespond(
                            withSuccess(projectCommitsContent, MediaType.APPLICATION_JSON_UTF8).headers(groupsHeaders));

            commitsDto = this.gitlabRetrieverService.getCommits(ID_PROJECT, pageInfoDTO);
        }

        assert commitsDto != null && commitsDto.getList() != null : "Return is invalid";
        assert commitsDto.getPageInfoDTO() != null : "Expect page info data";
        assert commitsDto.getPageInfoDTO().getTotal() != null : "Total info";

        revBasicCommit(commitsDto.getList().get(0));
    }

    @Test
    public void getSingleCommitTest() {
        this.server.expect(requestTo(getProjectSingleCommitUrlFormat.format(new Object[] { ID_PROJECT, COMMIT_HASH })))
                .andRespond(withSuccess(projectSingleCommitContent, MediaType.APPLICATION_JSON_UTF8));
        
        CommitDTO commitDTO = this.gitlabRetrieverService.getSingleCommit(ID_PROJECT, COMMIT_HASH);

        revBasicCommit(commitDTO);

        assertNotNull(commitDTO.getStats() != null);
        assertEquals(ID_PROJECT, commitDTO.getProjectId());
        assertEquals(new Integer(5), commitDTO.getStats().getAdditions());
        assertEquals(new Integer(5), commitDTO.getStats().getDeletions());
        assertEquals(new Integer(10), commitDTO.getStats().getTotal());
    }

    private void revBasicCommit(CommitDTO commitDTO) throws AssertionError {
        assertEquals("Expecto 'a6ef8ace9fc7adc8886d6f15c91a685fb61ad8a6' as ID",
                "a6ef8ace9fc7adc8886d6f15c91a685fb61ad8a6", commitDTO.getId());
        assertEquals("a6ef8ace", commitDTO.getShortId());
        assertEquals("Merge branch 'QA' into 'develop'", commitDTO.getTitle());
        assertNotNull(commitDTO.getParentIds());
        assertEquals(DateUtils.toDate("2018-04-02T14:27:05.000-05:00"), commitDTO.getCreatedAt());
        assertEquals(
                "Merge branch 'QA' into 'develop'\n\nQA to develop\n\nSee merge request repositorio-nacional/repositorio-institucional!19",
                commitDTO.getMessage());
        assertEquals("Victor Daniel Gutierrez Rodriguez", commitDTO.getAuthorName());
        assertEquals("vdaniel.gr@gmail.com", commitDTO.getAuthorEmail());
        assertEquals(DateUtils.toDate("2018-04-02T14:27:05.000-05:00"), commitDTO.getAuthoredDate());
        assertEquals("Victor Daniel Gutierrez Rodriguez", commitDTO.getCommitterName());
        assertEquals("vdaniel.gr@gmail.com", commitDTO.getCommitterEmail());
        assertEquals(DateUtils.toDate("2018-04-02T14:27:05.000-05:00"), commitDTO.getCommittedDate());
    }

}
