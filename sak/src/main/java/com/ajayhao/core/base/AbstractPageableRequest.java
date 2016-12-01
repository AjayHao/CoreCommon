package com.ajayhao.core.base;

import java.io.Serializable;

/**
 * 服务调用可分页请求参数基类<br/>
 *
 */
public abstract class AbstractPageableRequest extends AbstractRequest implements Serializable {
    private static final long serialVersionUID = 1521962742797959103L;

    /**
     * 当前所在的页码，必须大于等于1<br/>
     *
     */
    private Integer pageNo = 1;

    /**
     * 每页显示的条目数，必须大于等于1<br/>
     *
     */
    private Integer itemPerPage = 10;

    public AbstractPageableRequest() {
        ;
    }

    /**
     * 获取当前的所在的页码<br/>
     *
     * @return
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前的页码，必须大于等于1<br/>
     *
     * @param pageNo
     */
    public void setPageNo(Integer pageNo) {
        if(pageNo == null || pageNo <= 0) {
            throw new IllegalArgumentException("参数不合法");
        }

        this.pageNo = pageNo;
    }

    /**
     * 获取每页显示的条目数量<br/>
     *
     * @return
     */
    public Integer getItemPerPage() {
        return itemPerPage;
    }

    /**
     * 设置每页显示的条目数量，必须大于等于1<br/>
     *
     * @return
     */
    public void setItemPerPage(Integer itemPerPage) {
        if(itemPerPage == null || itemPerPage <= 0) {
            throw new IllegalArgumentException("参数不合法");
        }

        this.itemPerPage = itemPerPage;
    }

    @Override
    public String toString() {
        return "AbstractPageableRequest{" +
                "pageNo=" + pageNo +
                ", itemPerPage=" + itemPerPage +
                "} " + super.toString();
    }
}
