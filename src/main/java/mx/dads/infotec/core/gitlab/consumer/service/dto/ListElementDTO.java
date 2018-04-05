package mx.dads.infotec.core.gitlab.consumer.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author erik.valdivieso
 * @param <T> An serializable object
 */
public class ListElementDTO<T extends Serializable> implements Serializable {

    private List<T> list;
    private PageInfoDTO pageInfoDTO;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PageInfoDTO getPageInfoDTO() {
        return pageInfoDTO;
    }

    public void setPageInfoDTO(PageInfoDTO pageInfoDTO) {
        this.pageInfoDTO = pageInfoDTO;
    }
}
