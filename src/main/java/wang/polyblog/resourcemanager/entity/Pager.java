package wang.polyblog.resourcemanager.entity;

import java.util.List;

/**
 * 分页封装类
 * @param <T>: 分页数据
 */
public class Pager<T> {
    // 查询页码
    private Integer page;
    // 每页条数
    private Integer size;
    // 总记录数
    private Long total;

    private int total_page;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    //分页数据
    List<T> list;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "{page:" + this.page + ",size:" + this.size + ",total:" + this.total + ",total_page:" + this.page +
                ",list:" + this.list.toString() + "}";
    }
}
