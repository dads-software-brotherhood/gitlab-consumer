package mx.dads.infotec.core.gitlab.consumer.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitDTO implements Serializable {

    @JsonProperty("id")
    private String id;
    @JsonProperty("short_id")
    private String shortId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("parent_ids")
    private List<String> parentIds = null;
    @JsonProperty("message")
    private String message;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_email")
    private String authorEmail;
    @JsonProperty("authored_date")
    private String authoredDate;
    @JsonProperty("committer_name")
    private String committerName;
    @JsonProperty("committer_email")
    private String committerEmail;
    @JsonProperty("committed_date")
    private String committedDate;
    @JsonProperty("stats")
    private CommitStatsDTO stats;
    @JsonProperty("status")
    private Object status;
    @JsonProperty("last_pipeline")
    private Object lastPipeline;
    @JsonProperty("project_id")
    private Integer projectId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7082147429372623785L;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("short_id")
    public String getShortId() {
        return shortId;
    }

    @JsonProperty("short_id")
    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("parent_ids")
    public List<String> getParentIds() {
        return parentIds;
    }

    @JsonProperty("parent_ids")
    public void setParentIds(List<String> parentIds) {
        this.parentIds = parentIds;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("author_name")
    public String getAuthorName() {
        return authorName;
    }

    @JsonProperty("author_name")
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @JsonProperty("author_email")
    public String getAuthorEmail() {
        return authorEmail;
    }

    @JsonProperty("author_email")
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    @JsonProperty("authored_date")
    public String getAuthoredDate() {
        return authoredDate;
    }

    @JsonProperty("authored_date")
    public void setAuthoredDate(String authoredDate) {
        this.authoredDate = authoredDate;
    }

    @JsonProperty("committer_name")
    public String getCommitterName() {
        return committerName;
    }

    @JsonProperty("committer_name")
    public void setCommitterName(String committerName) {
        this.committerName = committerName;
    }

    @JsonProperty("committer_email")
    public String getCommitterEmail() {
        return committerEmail;
    }

    @JsonProperty("committer_email")
    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
    }

    @JsonProperty("committed_date")
    public String getCommittedDate() {
        return committedDate;
    }

    @JsonProperty("committed_date")
    public void setCommittedDate(String committedDate) {
        this.committedDate = committedDate;
    }

    @JsonProperty("stats")
    public CommitStatsDTO getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(CommitStatsDTO stats) {
        this.stats = stats;
    }

    @JsonProperty("status")
    public Object getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Object status) {
        this.status = status;
    }

    @JsonProperty("last_pipeline")
    public Object getLastPipeline() {
        return lastPipeline;
    }

    @JsonProperty("last_pipeline")
    public void setLastPipeline(Object lastPipeline) {
        this.lastPipeline = lastPipeline;
    }

    @JsonProperty("project_id")
    public Integer getProjectId() {
        return projectId;
    }

    @JsonProperty("project_id")
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
