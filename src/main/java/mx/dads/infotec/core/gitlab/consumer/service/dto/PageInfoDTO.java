package mx.dads.infotec.core.gitlab.consumer.service.dto;

import java.io.Serializable;

/**
 *
 * @author erik.valdivieso
 */
public class PageInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
	private Integer page;
    private Integer perPage;
    private Integer total;
    private Integer totalPages;

    public PageInfoDTO() {
    }

    public PageInfoDTO(Integer page) {
        this.page = page;
    }

    public PageInfoDTO(Integer page, Integer perPage) {
        this.page = page;
        this.perPage = perPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "PageInfoDTO{" + "page=" + page + ", perPage=" + perPage + ", total=" + total + ", totalPages=" + totalPages + '}';
    }

}
