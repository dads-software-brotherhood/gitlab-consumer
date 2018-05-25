package mx.dads.infotec.core.gitlab.consumer.service.dto;

import java.io.Serializable;
import java.util.Date;

public class DateFilterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date since;
    private Date until;

    public Date getSince() {
        return this.since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUntil() {
        return this.until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

}
