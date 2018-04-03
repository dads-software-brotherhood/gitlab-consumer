package mx.dads.infotec.core.gitlab.consumer.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionsDTO implements Serializable {

    private AccessDTO projectAccess;
    private AccessDTO groupAccess;
    private final static long serialVersionUID = -7056866224852616193L;

    public AccessDTO getProjectAccess() {
        return projectAccess;
    }

    public void setProjectAccess(AccessDTO projectAccess) {
        this.projectAccess = projectAccess;
    }

    public AccessDTO getGroupAccess() {
        return groupAccess;
    }

    public void setGroupAccess(AccessDTO groupAccess) {
        this.groupAccess = groupAccess;
    }

}
