package mx.dads.infotec.core.gitlab.consumer.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitDTO implements Serializable {

    private static final long serialVersionUID = 7082147429372623785L;

    @JsonProperty("id")
    private String id;
    @JsonProperty("short_id")
    private String shortId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("parent_ids")
    private transient List<String> parentIds = null;
    @JsonProperty("message")
    private String message;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_email")
    private String authorEmail;
    @JsonProperty("authored_date")
    private Date authoredDate;
    @JsonProperty("committer_name")
    private String committerName;
    @JsonProperty("committer_email")
    private String committerEmail;
    @JsonProperty("committed_date")
    private Date committedDate;
    @JsonProperty("stats")
    private Stats stats;
    @JsonProperty("status")
    private String status;
    @JsonProperty("last_pipeline")
    private Pipeline lastPipeline;
    @JsonProperty("project_id")
    private Integer projectId;

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
    public Date getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(Date createdAt) {
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
    public Date getAuthoredDate() {
        return authoredDate;
    }

    @JsonProperty("authored_date")
    public void setAuthoredDate(Date authoredDate) {
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
    public Date getCommittedDate() {
        return committedDate;
    }

    @JsonProperty("committed_date")
    public void setCommittedDate(Date committedDate) {
        this.committedDate = committedDate;
    }

    @JsonProperty("stats")
    public Stats getStats() {
        return stats;
    }

    @JsonProperty("stats")
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("last_pipeline")
    public Pipeline getLastPipeline() {
        return lastPipeline;
    }

    @JsonProperty("last_pipeline")
    public void setLastPipeline(Pipeline lastPipeline) {
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Stats implements Serializable {

        private static final long serialVersionUID = -27527697034091943L;

        @JsonProperty("additions")
        private Integer additions;
        @JsonProperty("deletions")
        private Integer deletions;
        @JsonProperty("total")
        private Integer total;

        @JsonProperty("additions")
        public Integer getAdditions() {
            return additions;
        }

        @JsonProperty("additions")
        public void setAdditions(Integer additions) {
            this.additions = additions;
        }

        @JsonProperty("deletions")
        public Integer getDeletions() {
            return deletions;
        }

        @JsonProperty("deletions")
        public void setDeletions(Integer deletions) {
            this.deletions = deletions;
        }

        @JsonProperty("total")
        public Integer getTotal() {
            return total;
        }

        @JsonProperty("total")
        public void setTotal(Integer total) {
            this.total = total;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Pipeline implements Serializable {

        private static final long serialVersionUID = 3837305041362953528L;

        @JsonProperty("id")
        private Integer id;
        @JsonProperty("ref")
        private String ref;
        @JsonProperty("sha")
        private String sha;
        @JsonProperty("status")
        private String status;

        @JsonProperty("id")
        public Integer getId() {
            return id;
        }

        @JsonProperty("id")
        public void setId(Integer id) {
            this.id = id;
        }

        @JsonProperty("ref")
        public String getRef() {
            return ref;
        }

        @JsonProperty("ref")
        public void setRef(String ref) {
            this.ref = ref;
        }

        @JsonProperty("sha")
        public String getSha() {
            return sha;
        }

        @JsonProperty("sha")
        public void setSha(String sha) {
            this.sha = sha;
        }

        @JsonProperty("status")
        public String getStatus() {
            return status;
        }

        @JsonProperty("status")
        public void setStatus(String status) {
            this.status = status;
        }
    }
}
