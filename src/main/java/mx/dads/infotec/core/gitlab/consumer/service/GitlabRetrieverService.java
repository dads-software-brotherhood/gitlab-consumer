package mx.dads.infotec.core.gitlab.consumer.service;

import org.springframework.web.client.RestClientException;

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
public interface GitlabRetrieverService {

    /**
     * Get groups list.
     *
     * @return Groups list with pagination info.
     * @throws RestClientException
     */
    ListElementDTO<GroupDTO> getGroups() throws RestClientException;

    /**
     * Get groups list with custom page.
     *
     * @param pageInfoDTO Pagintation info (page and perPAge attributes are
     *                    required).
     * @return Groups list with pagination info.
     * @throws RestClientException
     */
    ListElementDTO<GroupDTO> getGroups(PageInfoDTO pageInfoDTO) throws RestClientException;

    /**
     * Get group's projects list.
     *
     * @param idGroup Group ID
     * @return Project's group.
     * @throws RestClientException
     */
    ListElementDTO<ProjectDTO> getProjects(int idGroup) throws RestClientException;

    /**
     * Get group's projects with custom page.
     *
     * @param idGroup Group ID
     * @param pageInfoDTO Pagintation info (page and perPAge attributes are
     *                    required).
     * @return Project's group.
     * @throws RestClientException
     */
    ListElementDTO<ProjectDTO> getProjects(int idGroup, PageInfoDTO pageInfoDTO) throws RestClientException;

    /**
     * Retrieve a commit's project.
     * 
     * @param idProject Project ID.
     * @return Commits list with pagination info.
     * @throws RestClientException
     */
    ListElementDTO<CommitDTO> getCommits(int idProject) throws RestClientException;

    /**
     * Retrieve a commit's project with custom page.
     * 
     * @param idProject   Project ID.
     * @param pageInfoDTO Pagintation info (page and perPAge attributes are
     *                    required).
     * @return Commits list with pagination info.
     * @throws RestClientException
     */
    ListElementDTO<CommitDTO> getCommits(int idProject, PageInfoDTO pageInfoDTO) throws RestClientException;

    /**
     * Retrieve a single commit info.
     * 
     * @param idProject Project ID.
     * @param shaCommit Teh
     * @throws RestClientException
     */
    CommitDTO getSingleCommit(int idProject, String shaCommit) throws RestClientException;

}