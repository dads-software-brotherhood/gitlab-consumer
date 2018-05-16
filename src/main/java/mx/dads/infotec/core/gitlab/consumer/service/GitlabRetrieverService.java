package mx.dads.infotec.core.gitlab.consumer.service;

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
     * Groups api path.
     */
    String GROUPS = "/groups";
    /**
     * Firts part of projects groups path.
     */
    String PROJECTS_GROUP_PART1 = "/groups/";
    /**
     * Last part of projects groups path.
     */
    String PROJECTS_GROUP_PART2 = "/projects";

    /**
     * Get groups list.
     *
     * @return Groups list with pagination info.
     */
    public ListElementDTO<GroupDTO> getGroups();

    /**
     * Get groups list.
     *
     * @param pageInfoDTO Pagintation info (page and perPAge attributes are
     *                    required).
     * @return Groups list with pagination info.
     */
    public ListElementDTO<GroupDTO> getGroups(PageInfoDTO pageInfoDTO);

    /**
     * Get group's projects list.
     *
     * @param idGroup Group ID
     * @return Group
     */
    public ListElementDTO<ProjectDTO> getProjects(int idGroup);

}