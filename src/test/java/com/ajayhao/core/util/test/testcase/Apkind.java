package com.ajayhao.core.util.test.testcase;


import com.ajayhao.core.base.AbstractCodedEnum;

import java.io.Serializable;

/**
 * 交易码<br/>
 * <p/>
 * Created by wu.charles on 2014/12/27.
 */
public final class Apkind extends AbstractCodedEnum implements Serializable {
    private static final long serialVersionUID = 3434942520461036786L;

    /**
     * ************************************************
     * 账户类
     * *************************************************
     */
    public static final Apkind OPEN_ACCO_APP
            = new Apkind("OPEN_ACCO_APP", "001", "开户申请");

    public static final Apkind OPEN_ACCO_ACK
            = new Apkind("OPEN_ACCO_ACK", "101", "开户确认");

    public static final Apkind CLOSE_ACCO_APP
            = new Apkind("CLOSE_ACCO_APP", "002", "销户申请");

    public static final Apkind CLOSE_ACCO_ACK
            = new Apkind("CLOSE_ACCO_ACK", "102", "销户确认");

    public static final Apkind OPEN_TRADEACCO_APP
            = new Apkind("OPEN_TRADEACCO_APP", "008", "增加交易账户号申请");

    public static final Apkind OPEN_TRADEACCO_ACK
            = new Apkind("OPEN_TRADEACCO_ACK", "108", "增加交易账户号确认");

    public static final Apkind CLOSE_TRADEACCO_APP
            = new Apkind("CLOSE_TRADEACCO_APP", "009", "销户申请");

    public static final Apkind CLOSE_TRADEACCO_ACK
            = new Apkind("CLOSE_TRADEACCO_ACK", "109", "销户确认");


    /**
     * ************************************************
     * 交易类
     * *************************************************
     */
    public static final Apkind SUBSCRIBE_APP
            = new Apkind("SUBSCRIBE_APP", "020", "认购申请");

    public static final Apkind SUBSCRIBEE_ACK
            = new Apkind("SUBSCRIBEE_ACK", "120", "认购确认");

    public static final Apkind SUBSCRIBE_FUND_ACK
            = new Apkind("SUBSCRIBE_FUND_ACK", "130", "认购份额确认");

    public static final Apkind SUBSCRIBE_FAULT_ACK
            = new Apkind("SUBSCRIBE_FAULT_ACK", "149", "募集失败");


    public static final Apkind PURCHAS_APP
            = new Apkind("PURCHAS_APP", "022", "申购申请");

    public static final Apkind PURCHAS_ACK
            = new Apkind("PURCHAS_ACK", "122", "申购确认");


    public static final Apkind FIXED_PURCHAS_APP
            = new Apkind("FIXED_PURCHAS_APP", "039", "定投申请");

    public static final Apkind FIXED_PURCHAS_ACK
            = new Apkind("FIXED_PURCHAS_ACK", "139", "定投确认");


    public static final Apkind REDEEM_APP
            = new Apkind("REDEEM_APP", "024", "赎回申请");

    public static final Apkind REDEEM_ACK
            = new Apkind("REDEEM_ACK", "124", "赎回申请确认");

    public static final Apkind CONVERT_APP
            = new Apkind("CONVERT_APP", "036", "基金转换");

    public static final Apkind CONVERT_ACK
            = new Apkind("CONVERT_ACK", "136", "基金转换确认");

    public static final Apkind CONVERT_IN_ACK
            = new Apkind("CONVERT_IN_ACK", "137", "基金转换转入确认");

    public static final Apkind CONVERT_OUT_ACK
            = new Apkind("CONVERT_OUT_ACK", "138", "基金转换转出确认");

    public static final Apkind FREEZE_APP
            = new Apkind("FREEZE_APP", "031", "份额冻结申请");

    public static final Apkind FREEZE_ACK
            = new Apkind("FREEZE_ACK", "131", "份额冻结确认");

    public static final Apkind THAW_APP
            = new Apkind("THAW_APP", "032", "份额解冻申请");

    public static final Apkind THAW_ACK
            = new Apkind("THAW_ACK", "132", "份额解冻确认");

    public static final Apkind CANCEL
            = new Apkind("CANCEL", "052", "撤单、冲正");


    public static final Apkind MELONMD_APP
            = new Apkind("MELONMD_APP", "029", "设置分红方式申请");

    public static final Apkind MELONMD_ACK
            = new Apkind("MELONMD_ACK", "129", "设置分红方式确认");


    public static final Apkind FAST_REDEEM_APP
            = new Apkind("FAST_REDEEM_APP", "098", "快速过户申请");

    public static final Apkind FAST_REDEEM_ACK
            = new Apkind("FAST_REDEEM_ACK", "198", "快速过户确认");

    /**
     * ************************************************
     * TA发起交易类
     * *************************************************
     */
    public static final Apkind MANAGER_IN_ACK
            = new Apkind("MANAGER_IN_ACK", "127", "转托管转入确认");

    public static final Apkind UNTRADETRANS_IN_ACK
            = new Apkind("UNTRADETRANS_IN_ACK", "134", "非交易过户转入");

    public static final Apkind UNTRADETRANS_OUT_ACK
            = new Apkind("UNTRADETRANS_OUT_ACK", "135", "非交易过户转出");

    public static final Apkind FORCE_REDEEM_ACK
            = new Apkind("FORCE_REDEEM_ACK", "142", "强赎确认");

    public static final Apkind SHARE_BONUS_ACK
            = new Apkind("SHARE_BONUS_ACK", "143", "分红确认");

    public static final Apkind FORCE_ADD_ACK
            = new Apkind("FORCE_ADD_ACK", "144", "强制调增确认");

    public static final Apkind FORCE_REDUCE_ACK
            = new Apkind("FORCE_REDUCE_ACK", "145", "强制调减确认");

    public static final Apkind LIQUIDATE_ACK
            = new Apkind("LIQUIDATE_ACK", "150", "基金清盘确认");

    protected Apkind() {
        ; // 解决反序列化无法构造新实例的问题！！
    }

    protected Apkind(String name, String code, String desc) {
        super(name, code, desc);
    }

    @Override
    protected Class<? extends AbstractCodedEnum> getEnumType() {
        return Apkind.class;
    }
}
