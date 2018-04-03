package mx.dads.infotec.core.gitlab.consumer.service.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Gitlab group and project access response info.
 * 
 * @see https://docs.gitlab.com/ee/api/groups.html
 * @author erik.valdivieso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessDTO implements Serializable {

    private final static long serialVersionUID = 4225399062237425426L;
    
    private Integer accessLevel;
    private Integer notificationLevel;

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Integer getNotificationLevel() {
        return notificationLevel;
    }

    public void setNotificationLevel(Integer notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

}
