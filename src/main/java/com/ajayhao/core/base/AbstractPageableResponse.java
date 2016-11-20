package com.ajayhao.core.base;

import java.io.Serializable;

/**
 * 服务调用可分页请求返回值基类<br/>
 *
 */
public abstract class AbstractPageableResponse extends AbstractResponse implements Serializable {
    private static final long serialVersionUID = -6285389178973768018L;

    /**
     * 当前页总条目数量<br/>
     *
     */
    private Integer totalItem = 0;

    public AbstractPageableResponse() {
        ;
    }

    /**
     * 获取当前页总条目数量<br/>
     *
     * @return
     */
    public Integer getTotalItem() {
        return totalItem;
    }

    /**
     * 设置当前页总条目数量，必须大于等于0<br/>
     *
     * @param totalItem
     */
    public void setTotalItem(Integer totalItem) {
        if(totalItem == null || totalItem < 0) {
            throw new IllegalArgumentException("参数不合法");
        }

        this.totalItem = totalItem;
    }

    @Override
    public String toString() {
        return "AbstractPageableResponse{" +
                "totalItem=" + totalItem +
                "} " + super.toString();
    }
}
