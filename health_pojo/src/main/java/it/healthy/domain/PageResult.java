package it.healthy.domain;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private Long total; //总记录数
    private List rows; //当前页结果

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public PageResult() {
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }
}
