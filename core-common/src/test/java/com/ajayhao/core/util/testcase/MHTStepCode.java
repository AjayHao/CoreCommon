package com.ajayhao.core.util.testcase;

import com.ajayhao.core.base.AbstractEnum;

public class MHTStepCode extends AbstractEnum {
    public static final MHTStepCode PURCHASE = new MHTStepCode("0001", "申购");
    public static final MHTStepCode REDEEM = new MHTStepCode("0002", "赎回");
    public static final MHTStepCode FAST_REDEEM = new MHTStepCode("0003", "快赎");
    public static final MHTStepCode FREEZE = new MHTStepCode("0004", "冻结");
    public static final MHTStepCode UNFREEZE = new MHTStepCode("0005", "解冻");
    public static final MHTStepCode CANCEL = new MHTStepCode("0006", "撤单");
    //    public static final MHTStepCode CORRECTION = new MHTStepCode( "0007", "冲正");
    public static final MHTStepCode SUBSCRIBE = new MHTStepCode("0008", "认购");
    public static final MHTStepCode AIP = new MHTStepCode("0009", "定投");
    public static final MHTStepCode CONVERT = new MHTStepCode("0010", "转换");
    public static final MHTStepCode SET_MELON = new MHTStepCode("0011", "设置分红方式");
    public static final MHTStepCode FAIL = new MHTStepCode("0012", "失败");

    public static final MHTStepCode RECHARGE = new MHTStepCode("0101", "充值(赎回后购买滚钱宝)");
    public static final MHTStepCode WITHDRAW = new MHTStepCode("0102", "提现(慢赎滚钱宝后申购)");
    public static final MHTStepCode PRE_RECHARGE = new MHTStepCode("0103", "充值(用于预约购买)");
    public static final MHTStepCode PRE_WITHDRAW = new MHTStepCode("0104", "提现(用于预约购买)");
    //TA发起的赎回确认,用于清盘,强赎
    public static final MHTStepCode REDEEM_ACK = new MHTStepCode("0105", "TA发起的赎回确认");
    //TA发起的申购确认,用于分红再投,强增
    public static final MHTStepCode QUTY_ADD = new MHTStepCode("0106", "TA发起的申购确认");
    public static final MHTStepCode PRE_FREEZE = new MHTStepCode("0107", "冻结(用于预约购买)");
    public static final MHTStepCode PRE_UNFREEZE = new MHTStepCode("0108", "解冻(用于预约购买)");
    public static final MHTStepCode REFUND_RECHARGE = new MHTStepCode("0109",
            "退款充值(用于撤单,申购失败或部分成功退款充值滚钱宝)");
    //TA发起的出款,用于现金分红
    public static final MHTStepCode CASH_OUT = new MHTStepCode("0110", "TA发起的出款");
    //TA发起的份额扣减,用于强减
    public static final MHTStepCode QUTY_REDUCE = new MHTStepCode("0111", "TA发起的强减");
    //赎回多次确认
    public static final MHTStepCode REDEEM_MULTI_ACK = new MHTStepCode("0112", "赎回多次确认(针对巨额赎回顺延)");
    //赎回多次确认
    public static final MHTStepCode MULTI_ACK_RECHARGE = new MHTStepCode("0113", "赎回多次确认后充值(针对巨额赎回顺延)");
    //TA发起的份额冻结
    public static final MHTStepCode QUTY_FROZEN = new MHTStepCode("0114", "TA发起的份额冻结");
    public static final MHTStepCode QUTY_UNFROZEN = new MHTStepCode("0115", "TA发起的份额解冻");

    //预约类的交易,等待再次发起
    public static final MHTStepCode WAIT_UNFREEZE = new MHTStepCode("1001", "等待解冻发起");
    public static final MHTStepCode WAIT_PURCHASE = new MHTStepCode("1002", "等待申购发起");
    public static final MHTStepCode WAIT_CANCEL = new MHTStepCode("1003", "等待撤单发起");
    public static final MHTStepCode WAIT_REFUND = new MHTStepCode("1004", "等待退款完成");
    public static final MHTStepCode WAIT_CASH_OUT = new MHTStepCode("1005", "等待出款完成");


    protected MHTStepCode() {
        ; // 解决反序列化无法构造新实例的问题！！
    }

    protected MHTStepCode(String name, String desc) {
        super(name, desc);
    }

    @Override
    protected Class<? extends AbstractEnum> getEnumType() {
        return MHTStepCode.class;
    }

}