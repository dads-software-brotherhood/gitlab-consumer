package mx.dads.infotec.core.gitlab.consumer.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 * @author erik.valdivieso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BasicGitlabElementDTO implements Serializable {

    private static final long serialVersionUID = 7356584600677895L;

    private Integer id;
    private String name;
    private String path;

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
}
