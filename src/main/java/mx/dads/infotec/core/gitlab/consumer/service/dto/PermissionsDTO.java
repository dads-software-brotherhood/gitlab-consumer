package mx.dads.infotec.core.gitlab.consumer.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionsDTO implements Serializable {

    private Object projectAccess;
    private GroupAccessDTO groupAccess;
    private final static long serialVersionUID = -7056866224852616193L;

    public Object getProjectAccess() {
        return projectAccess;
    }

    public void setProjectAccess(Object projectAccess) {
        this.projectAccess = projectAccess;
    }

    public GroupAccessDTO getGroupAccess() {
        return groupAccess;
    }

    public void setGroupAccess(GroupAccessDTO groupAccess) {
        this.groupAccess = groupAccess;
    }

}
