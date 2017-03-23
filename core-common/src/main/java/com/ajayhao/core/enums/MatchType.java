package com.ajayhao.core.enums;

import com.ajayhao.core.base.AbstractEnum;

import java.io.Serializable;

/**
 * 匹配类型<br/>
 */
public class MatchType extends AbstractEnum implements Serializable {
    private static final long serialVersionUID = -6044821533007872406L;

    public static final MatchType Similar = new MatchType("S", "相似（兼容）");

    public static final MatchType Identical = new MatchType("I", "完全一致");

    public static final MatchType NotMatch = new MatchType("N", "不匹配");

    protected MatchType() {
        super();
    }

    protected MatchType(String name, String desc) {
        super(name, desc);
    }

    @Override
    protected Class<? extends AbstractEnum> getEnumType() {
        return MatchType.class;
    }
}
