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
 *
 * @author erik.valdivieso
 */
@Service
public class GitlabRetrieverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitlabRetrieverService.class);

    private String getGroupsUrl;

    @Autowired
    private ApplicationProperties applicationProperties;
    
    @PostConstruct
    protected void init() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GitLab URL : {}", applicationProperties.getGitlab().getApiUrl());
        }

        getGroupsUrl = applicationProperties.getGitlab().getApiUrl() + "/groups";
    }
    
    public ListElementDTO<GroupDTO> getGroups(PageInfoDTO pageInfoDTO) {
        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder ucb = UriComponentsBuilder.fromHttpUrl(getGroupsUrl);

        if (pageInfoDTO != null && pageInfoDTO.getPage() != null) {
            ucb.queryParam("page", pageInfoDTO.getPage());

            if (pageInfoDTO.getPerPage() != null) {
                ucb.queryParam("per_page", pageInfoDTO.getPerPage());
            }
        }

        URI uri = ucb.build().encode().toUri();

        LOGGER.debug("URI: {}", uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set(applicationProperties.getGitlab().getSecurity().getTokenHeaderName(), applicationProperties.getGitlab().getSecurity().getToken());

        HttpEntity<String> httpEntity = new HttpEntity<>("parameters", headers);

        ResponseEntity<GroupDTO[]> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, GroupDTO[].class);

        ListElementDTO<GroupDTO> listElementDTO = new ListElementDTO<>();

        listElementDTO.setList(Arrays.asList(responseEntity.getBody()));
        listElementDTO.setPageInfoDTO(buildPageInfoDTO(responseEntity.getHeaders()));

        return listElementDTO;
    }

    public ListElementDTO<ProjectDTO> getProjects(int idGroup) {
        return null;
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
