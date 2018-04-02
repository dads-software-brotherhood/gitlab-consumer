
package mx.dads.infotec.core.gitlab.consumer.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LinksDTO implements Serializable {

    private final static long serialVersionUID = 2783751251249165910L;

    private String self;
    private String issues;
    private String mergeRequests;
    private String repoBranches;
    private String labels;
    private String events;
    private String members;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getIssues() {
        return issues;
    }

    public void setIssues(String issues) {
        this.issues = issues;
    }

    public String getMergeRequests() {
        return mergeRequests;
    }

    public void setMergeRequests(String mergeRequests) {
        this.mergeRequests = mergeRequests;
    }

    public String getRepoBranches() {
        return repoBranches;
    }

    public void setRepoBranches(String repoBranches) {
        this.repoBranches = repoBranches;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

}
