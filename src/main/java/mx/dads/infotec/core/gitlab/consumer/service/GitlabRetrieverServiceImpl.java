package mx.dads.infotec.core.gitlab.consumer.service;

import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.GROUPS;
import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.GROUP_PROJECTS;
import static mx.dads.infotec.core.gitlab.consumer.service.ServiceConstants.PROJECT_COMMITS;

import java.io.Serializable;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import mx.dads.infotec.core.gitlab.consumer.config.ApplicationProperties;
import mx.dads.infotec.core.gitlab.consumer.service.dto.CommitDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.GroupDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ListElementDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.PageInfoDTO;
import mx.dads.infotec.core.gitlab.consumer.service.dto.ProjectDTO;

/**
 * Internal service for retrieve gitlab info from gitlab api v4.
 *
 * @author erik.valdivieso
 */
@Service
public class GitlabRetrieverServiceImpl implements GitlabRetrieverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabRetrieverServiceImpl.class);

    private String getGroupsUrl;

    private MessageFormat getGroupProjectsUrlFormat;
    private MessageFormat getProjectCommitsUrlFormat;

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
        getGroupProjectsUrlFormat = new MessageFormat(applicationProperties.getGitlab().getApiUrl() + GROUP_PROJECTS);
        getProjectCommitsUrlFormat = new MessageFormat(applicationProperties.getGitlab().getApiUrl() + PROJECT_COMMITS);
    }

    @Override
    public ListElementDTO<GroupDTO> getGroups() throws RestClientException {
        return getGroups(null);
    }

    @Override
    public ListElementDTO<GroupDTO> getGroups(PageInfoDTO pageInfoDTO) throws RestClientException {
        URI uri = buildUri(getGroupsUrl, pageInfoDTO);
        ResponseEntity<GroupDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, buildAuthHeaders(),
                GroupDTO[].class);
        return buiListElementDTO(responseEntity);
    }

    @Override
    public ListElementDTO<ProjectDTO> getProjects(int idGroup) throws RestClientException {
        return getProjects(idGroup, null);
    }

    @Override
    public ListElementDTO<ProjectDTO> getProjects(int idGroup, PageInfoDTO pageInfoDTO) throws RestClientException {
        URI uri = buildUri(getGroupProjectsUrlFormat.format(new Object[] { idGroup }), pageInfoDTO);
        ResponseEntity<ProjectDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, buildAuthHeaders(),
                ProjectDTO[].class);
        return buiListElementDTO(responseEntity);
    }

    @Override
    public ListElementDTO<CommitDTO> getCommits(int idProject) throws RestClientException {
        return getCommits(idProject, null);
    }

    @Override
    public ListElementDTO<CommitDTO> getCommits(int idProject, PageInfoDTO pageInfoDTO) throws RestClientException {
        URI uri = buildUri(getProjectCommitsUrlFormat.format(new Object[] { idProject }), pageInfoDTO);
        ResponseEntity<CommitDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, buildAuthHeaders(),
                CommitDTO[].class);
        return buiListElementDTO(responseEntity);
    }

    @Override
    public CommitDTO getSingleCommit(int idProject, String shaCommit) throws RestClientException {
        return null;
    }

    private URI buildUri(String url, PageInfoDTO pageInfoDTO) {
        UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(url);

        if (pageInfoDTO != null && pageInfoDTO.getPage() != null) {
            ucb.queryParam("page", pageInfoDTO.getPage());

            if (pageInfoDTO.getPerPage() != null) {
                ucb.queryParam("per_page", pageInfoDTO.getPerPage());
            }
        }

        URI uri = ucb.build().encode().toUri();
        LOGGER.debug("URI: {}", uri);
        return uri;
    }

    private <T extends Serializable> ListElementDTO<T> buiListElementDTO(ResponseEntity<T[]> responseEntity) {
        ListElementDTO<T> listElementDTO = new ListElementDTO<>();
        listElementDTO.setList(Arrays.asList(responseEntity.getBody()));
        listElementDTO.setPageInfoDTO(buildPageInfoDTO(responseEntity.getHeaders()));
        return listElementDTO;
    }

    private HttpEntity<?> buildAuthHeaders() {
        HttpEntity<?> httpEntity;

        if (applicationProperties.getGitlab().getSecurity().getToken() != null
                && !applicationProperties.getGitlab().getSecurity().getToken().isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(applicationProperties.getGitlab().getSecurity().getTokenHeaderName(),
                    applicationProperties.getGitlab().getSecurity().getToken());
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

}
