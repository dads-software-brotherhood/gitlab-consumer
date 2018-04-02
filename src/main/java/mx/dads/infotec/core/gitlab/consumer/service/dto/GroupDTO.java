package mx.dads.infotec.core.gitlab.consumer.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author erik.valdivieso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDTO implements Serializable {

    private Integer id;
    private String name;
    private String path;
    private String description;
    private String visibility;
    private Boolean lfsEnabled;
    private String avatarUrl;
    private String webUrl;
    private Boolean requestAccessEnabled;
    private String fullName;
    private String fullPath;
    private Integer parentId;

    private Statistics statistics;
    private List<GroupDTO> subgroups;
    private List<ProjectDTO> projects = new ArrayList<>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Boolean getLfsEnabled() {
        return lfsEnabled;
    }

    public void setLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Boolean getRequestAccessEnabled() {
        return requestAccessEnabled;
    }

    public void setRequestAccessEnabled(Boolean requestAccessEnabled) {
        this.requestAccessEnabled = requestAccessEnabled;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public List<GroupDTO> getSubgroups() {
        return subgroups;
    }

    public void setSubgroups(List<GroupDTO> subgroups) {
        this.subgroups = subgroups;
    }

    public class Statistics {

        private int storageSize;
        private int repositorySize;
        private int lfsObjectsSize;
        private int jobArtifactsSize;

        public int getStorageSize() {
            return storageSize;
        }

        public void setStorageSize(int storageSize) {
            this.storageSize = storageSize;
        }

        public int getRepositorySize() {
            return repositorySize;
        }

        public void setRepositorySize(int repositorySize) {
            this.repositorySize = repositorySize;
        }

        public int getLfsObjectsSize() {
            return lfsObjectsSize;
        }

        public void setLfsObjectsSize(int lfsObjectsSize) {
            this.lfsObjectsSize = lfsObjectsSize;
        }

        public int getJobArtifactsSize() {
            return jobArtifactsSize;
        }

        public void setJobArtifactsSize(int jobArtifactsSize) {
            this.jobArtifactsSize = jobArtifactsSize;
        }

        @Override
        public String toString() {
            return "Statistics{" + "storageSize=" + storageSize + ", repositorySize=" + repositorySize + ", lfsObjectsSize=" + lfsObjectsSize + ", jobArtifactsSize=" + jobArtifactsSize + '}';
        }

    }
}
