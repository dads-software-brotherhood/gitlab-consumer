package mx.dads.infotec.core.gitlab.consumer.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO implements Serializable {

    private static final long serialVersionUID = -7969787330924185559L;

    private Integer id;
    private String description;
    private String name;
    private String nameWithNamespace;
    private String path;
    private String pathWithNamespace;
    private Date createdAt;
    private String defaultBranch;
    private transient List<String> tagList = null;
    private String sshUrlToRepo;
    private String httpUrlToRepo;
    private String webUrl;
    private String avatarUrl;
    private Integer starCount;
    private Integer forksCount;
    private Date lastActivityAt;
    @JsonProperty("_links")
    private LinksDTO links;
    private Boolean archived;
    private String visibility;
    private Boolean resolveOutdatedDiffDiscussions;
    private Boolean containerRegistryEnabled;
    private Boolean issuesEnabled;
    private Boolean mergeRequestsEnabled;
    private Boolean wikiEnabled;
    private Boolean jobsEnabled;
    private Boolean snippetsEnabled;
    private Boolean sharedRunnersEnabled;
    private Boolean lfsEnabled;
    private Integer creatorId;
    private NamespaceDTO namespace;
    private String importStatus;
    private transient Object importError;
    private Integer openIssuesCount;
    private String runnersToken;
    private Boolean publicJobs;
    private String ciConfigPath;
    private transient List<GroupDTO> sharedWithGroups = null;
    private Boolean onlyAllowMergeIfPipelineSucceeds;
    private Boolean requestAccessEnabled;
    private Boolean onlyAllowMergeIfAllDiscussionsAreResolved;
    private Boolean printingMergeRequestLinkEnabled;
    private PermissionsDTO permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameWithNamespace() {
        return nameWithNamespace;
    }

    public void setNameWithNamespace(String nameWithNamespace) {
        this.nameWithNamespace = nameWithNamespace;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPathWithNamespace() {
        return pathWithNamespace;
    }

    public void setPathWithNamespace(String pathWithNamespace) {
        this.pathWithNamespace = pathWithNamespace;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getSshUrlToRepo() {
        return sshUrlToRepo;
    }

    public void setSshUrlToRepo(String sshUrlToRepo) {
        this.sshUrlToRepo = sshUrlToRepo;
    }

    public String getHttpUrlToRepo() {
        return httpUrlToRepo;
    }

    public void setHttpUrlToRepo(String httpUrlToRepo) {
        this.httpUrlToRepo = httpUrlToRepo;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    public Date getLastActivityAt() {
        return lastActivityAt;
    }

    public void setLastActivityAt(Date lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public LinksDTO getLinks() {
        return links;
    }

    public void setLinks(LinksDTO links) {
        this.links = links;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Boolean getResolveOutdatedDiffDiscussions() {
        return resolveOutdatedDiffDiscussions;
    }

    public void setResolveOutdatedDiffDiscussions(Boolean resolveOutdatedDiffDiscussions) {
        this.resolveOutdatedDiffDiscussions = resolveOutdatedDiffDiscussions;
    }

    public Boolean getContainerRegistryEnabled() {
        return containerRegistryEnabled;
    }

    public void setContainerRegistryEnabled(Boolean containerRegistryEnabled) {
        this.containerRegistryEnabled = containerRegistryEnabled;
    }

    public Boolean getIssuesEnabled() {
        return issuesEnabled;
    }

    public void setIssuesEnabled(Boolean issuesEnabled) {
        this.issuesEnabled = issuesEnabled;
    }

    public Boolean getMergeRequestsEnabled() {
        return mergeRequestsEnabled;
    }

    public void setMergeRequestsEnabled(Boolean mergeRequestsEnabled) {
        this.mergeRequestsEnabled = mergeRequestsEnabled;
    }

    public Boolean getWikiEnabled() {
        return wikiEnabled;
    }

    public void setWikiEnabled(Boolean wikiEnabled) {
        this.wikiEnabled = wikiEnabled;
    }

    public Boolean getJobsEnabled() {
        return jobsEnabled;
    }

    public void setJobsEnabled(Boolean jobsEnabled) {
        this.jobsEnabled = jobsEnabled;
    }

    public Boolean getSnippetsEnabled() {
        return snippetsEnabled;
    }

    public void setSnippetsEnabled(Boolean snippetsEnabled) {
        this.snippetsEnabled = snippetsEnabled;
    }

    public Boolean getSharedRunnersEnabled() {
        return sharedRunnersEnabled;
    }

    public void setSharedRunnersEnabled(Boolean sharedRunnersEnabled) {
        this.sharedRunnersEnabled = sharedRunnersEnabled;
    }

    public Boolean getLfsEnabled() {
        return lfsEnabled;
    }

    public void setLfsEnabled(Boolean lfsEnabled) {
        this.lfsEnabled = lfsEnabled;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public NamespaceDTO getNamespace() {
        return namespace;
    }

    public void setNamespace(NamespaceDTO namespace) {
        this.namespace = namespace;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public Object getImportError() {
        return importError;
    }

    public void setImportError(Object importError) {
        this.importError = importError;
    }

    public Integer getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setOpenIssuesCount(Integer openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    public String getRunnersToken() {
        return runnersToken;
    }

    public void setRunnersToken(String runnersToken) {
        this.runnersToken = runnersToken;
    }

    public Boolean getPublicJobs() {
        return publicJobs;
    }

    public void setPublicJobs(Boolean publicJobs) {
        this.publicJobs = publicJobs;
    }

    public String getCiConfigPath() {
        return ciConfigPath;
    }

    public void setCiConfigPath(String ciConfigPath) {
        this.ciConfigPath = ciConfigPath;
    }

    public List<GroupDTO> getSharedWithGroups() {
        return sharedWithGroups;
    }

    public void setSharedWithGroups(List<GroupDTO> sharedWithGroups) {
        this.sharedWithGroups = sharedWithGroups;
    }

    public Boolean getOnlyAllowMergeIfPipelineSucceeds() {
        return onlyAllowMergeIfPipelineSucceeds;
    }

    public void setOnlyAllowMergeIfPipelineSucceeds(Boolean onlyAllowMergeIfPipelineSucceeds) {
        this.onlyAllowMergeIfPipelineSucceeds = onlyAllowMergeIfPipelineSucceeds;
    }

    public Boolean getRequestAccessEnabled() {
        return requestAccessEnabled;
    }

    public void setRequestAccessEnabled(Boolean requestAccessEnabled) {
        this.requestAccessEnabled = requestAccessEnabled;
    }

    public Boolean getOnlyAllowMergeIfAllDiscussionsAreResolved() {
        return onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public void setOnlyAllowMergeIfAllDiscussionsAreResolved(Boolean onlyAllowMergeIfAllDiscussionsAreResolved) {
        this.onlyAllowMergeIfAllDiscussionsAreResolved = onlyAllowMergeIfAllDiscussionsAreResolved;
    }

    public Boolean getPrintingMergeRequestLinkEnabled() {
        return printingMergeRequestLinkEnabled;
    }

    public void setPrintingMergeRequestLinkEnabled(Boolean printingMergeRequestLinkEnabled) {
        this.printingMergeRequestLinkEnabled = printingMergeRequestLinkEnabled;
    }

    public PermissionsDTO getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionsDTO permissions) {
        this.permissions = permissions;
    }

}
