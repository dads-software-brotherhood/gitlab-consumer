package mx.dads.infotec.core.gitlab.consumer.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class NamespaceDTO extends BasicGitlabElementDTO implements Serializable {

    private static final long serialVersionUID = 7356589274600677895L;

    private String kind;
    @JsonProperty("full_path")
    private String fullPath;
    @JsonProperty("parent_id")
    private Integer parentId;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
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

    @Override
    public String toString() {
        return "Namespace{" + "id=" + getId() + ", name=" + getName() + ", path=" + getPath() + ", kind=" + kind + ", fullPath=" + fullPath + ", parentId=" + parentId + '}';
    }

}
