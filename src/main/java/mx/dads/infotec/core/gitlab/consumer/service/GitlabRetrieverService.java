package mx.dads.infotec.core.gitlab.consumer.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import mx.dads.infotec.core.gitlab.consumer.config.ApplicationProperties;
import mx.dads.infotec.core.gitlab.consumer.service.dto.GroupDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ListElementDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.PageInfoDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Internal service for retrieve gitlab info from gitlab api v4.
 *
 * @author erik.valdivieso
 */
@Service
public class GitlabRetrieverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabRetrieverService.class);

    /**
     * Groups api path.
     */
    public static final String GROUPS = "/groups";
    /**
     * Firts part of projects groups path.
     */
    public static final String PROJECTS_GROUP_PART1 = "/groups/";
    /**
     * Last part of projects groups path.
     */
    public static final String PROJECTS_GROUP_PART2 = "/projects";

    private String getGroupsUrl;
    private String getProjectsUrlPart;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    protected void init() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GitLab URL : {}", applicationProperties.getGitlab().getApiUrl());
        }

        getGroupsUrl = applicationProperties.getGitlab().getApiUrl() + GROUPS;
        getProjectsUrlPart = applicationProperties.getGitlab().getApiUrl() + PROJECTS_GROUP_PART1;
    }

    /**
     * Get groups list.
     *
     * @return Groups list with pagination info.
     */
    public ListElementDTO<GroupDTO> getGroups() {
        return getGroups(null);
    }

    /**
     * Get groups list.
     *
     * @param pageInfoDTO Pagintation info (page and perPAge attributes are
     * required).
     * @return Groups list with pagination info.
     */
    public ListElementDTO<GroupDTO> getGroups(PageInfoDTO pageInfoDTO) {
        UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(getGroupsUrl);

        if (pageInfoDTO != null && pageInfoDTO.getPage() != null) {
            ucb.queryParam("page", pageInfoDTO.getPage());

            if (pageInfoDTO.getPerPage() != null) {
                ucb.queryParam("per_page", pageInfoDTO.getPerPage());
            }
        }

        URI uri = ucb.build().encode().toUri();

        LOGGER.debug("URI: {}", uri);

        ResponseEntity<GroupDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, buildAuthHeaders(), GroupDTO[].class);

        ListElementDTO<GroupDTO> listElementDTO = new ListElementDTO<>();

        listElementDTO.setList(Arrays.asList(responseEntity.getBody()));
        listElementDTO.setPageInfoDTO(buildPageInfoDTO(responseEntity.getHeaders()));

        return listElementDTO;
    }

    /**
     * Get group's projects list.
     *
     * @param idGroup Group ID
     * @return Group
     */
    public ListElementDTO<ProjectDTO> getProjects(int idGroup) {
        ResponseEntity<ProjectDTO[]> responseEntity = restTemplate.exchange(buildProjectGroupsUrl(idGroup), HttpMethod.GET, buildAuthHeaders(), ProjectDTO[].class);

        ListElementDTO<ProjectDTO> listElementDTO = new ListElementDTO<>();
        listElementDTO.setList(Arrays.asList(responseEntity.getBody()));

        return listElementDTO;
    }

    private HttpEntity<?> buildAuthHeaders() {
        HttpEntity<?> httpEntity;

        if (applicationProperties.getGitlab().getSecurity().getToken() != null && !applicationProperties.getGitlab().getSecurity().getToken().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(applicationProperties.getGitlab().getSecurity().getTokenHeaderName(), applicationProperties.getGitlab().getSecurity().getToken());
            httpEntity = new HttpEntity<>("parameters", headers);
        } else {
            httpEntity = HttpEntity.EMPTY;
        }

        return httpEntity;
    }

    private PageInfoDTO buildPageInfoDTO(HttpHeaders headers) {

        PageInfoDTO pageInfoDTO = new PageInfoDTO();

        pageInfoDTO.setPage(getKeyAsNum("X-Page", headers));
        pageInfoDTO.setPerPage(getKeyAsNum("X-Per-Page", headers));
        pageInfoDTO.setTotal(getKeyAsNum("X-Total", headers));
        pageInfoDTO.setTotalPages(getKeyAsNum("X-Total-Pages", headers));

        return pageInfoDTO;

    }

    private String getKey(String key, HttpHeaders headers) {
        if (headers.containsKey(key)) {
            List<String> tmp = headers.get(key);
            if (tmp != null && !tmp.isEmpty()) {
                return tmp.get(0);
            }
        }

        return null;
    }

    private Integer getKeyAsNum(String key, HttpHeaders headers) {
        String tmp = getKey(key, headers);

        try {
            return Integer.parseInt(tmp);
        } catch (NumberFormatException | NullPointerException ex) {
            LOGGER.debug("Error at parse num", ex);
        }

        return null;
    }

    private String buildProjectGroupsUrl(int idGroup) {
        StringBuilder sb = new StringBuilder();
        sb.append(getProjectsUrlPart).append(idGroup).append(PROJECTS_GROUP_PART2);
        return sb.toString();
    }
}
