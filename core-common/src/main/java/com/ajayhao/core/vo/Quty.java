package com.ajayhao.core.vo;

import com.ajayhao.core.enums.BizCode;
import com.ajayhao.core.util.CoreCommonUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by AjayHao on 2017/6/16.
 */
public final class Quty implements Serializable, Comparable<Quty> {
    public static final int DEFAULT_SCALE = 2;
    public static final int DEFAULT_ROUNDING_MODE = 4;
    private long value;
    private int scale;

    public Quty() {
        this(0L, 2);
    }

    public Quty(long value, int scale) {
        this.value = 0L;
        this.scale = -1;
        if(scale < 0) {
            CoreCommonUtils.raiseBizException(BizCode.ParamError, "scale必须大于等于0");
        }

        this.value = value;
        this.scale = scale;
    }

    public Quty(BigDecimal value, int scale) {
        this(value, scale, 4);
    }

    public Quty(BigDecimal value, int scale, int roundingMode) {
        this.value = 0L;
        this.scale = -1;
        this.scale = scale;
        BigDecimal newValue = value.movePointRight(scale);
        this.value = newValue.setScale(0, roundingMode).longValue();
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public int getScale() {
        return this.scale;
    }

    public BigDecimal toFloat() {
        return new BigDecimal(BigInteger.valueOf(this.value), this.scale);
    }

    public int compareTo(Quty o) {
        return this.toFloat().compareTo(o.toFloat());
    }

    public int hashCode() {
        return (int)(this.value ^ this.value >>> 32);
    }

    public boolean equals(Object obj) {
        return this == obj?true:(obj instanceof Quty?this.toFloat().equals(((Quty)obj).toFloat()):false);
    }

    public String toString() {
        return this.toFloat().toString();
    }

    public Quty add(Quty quty) {
        this.assertSameQuty(this, quty);
        return this.add(quty, 4);
    }

    public Quty add(Quty quty, int roundingMode) {
        this.assertSameQuty(this, quty);
        BigDecimal result = this.addOp(quty, roundingMode);
        return new Quty(result.longValue(), this.scale);
    }

    private BigDecimal addOp(Quty quty, int roundingMode) {
        BigDecimal _this = this.toFloat();
        BigDecimal _other = quty.toFloat();
        BigDecimal result = _this.add(_other);
        result = result.movePointRight(this.scale);
        result = result.setScale(0, roundingMode);
        return result;
    }

    public Quty addTo(Quty quty) {
        this.assertSameQuty(this, quty);
        return this.addTo(quty, 4);
    }

    public Quty addTo(Quty quty, int roundingMode) {
        this.assertSameQuty(this, quty);
        BigDecimal result = this.addOp(quty, roundingMode);
        this.value = result.longValue();
        return this;
    }

    public Quty subtract(Quty quty) {
        this.assertSameQuty(this, quty);
        return this.subtract(quty, 4);
    }

    public Quty subtract(Quty quty, int roundingMode) {
        this.assertSameQuty(this, quty);
        BigDecimal result = this.subtractOp(quty, roundingMode);
        return new Quty(result.longValue(), this.scale);
    }

    public Quty subtractFrom(Quty quty) {
        this.assertSameQuty(this, quty);
        return this.subtractFrom(quty, 4);
    }

    public Quty subtractFrom(Quty quty, int roundingMode) {
        this.assertSameQuty(this, quty);
        BigDecimal result = this.subtractOp(quty, roundingMode);
        this.value = result.longValue();
        return this;
    }

    private BigDecimal subtractOp(Quty quty, int roundingMode) {
        BigDecimal _this = this.toFloat();
        BigDecimal _other = quty.toFloat();
        BigDecimal result = _this.subtract(_other);
        result = result.movePointRight(this.scale);
        result = result.setScale(0, roundingMode);
        return result;
    }

    public Quty multiply(long quty) {
        return new Quty(this.value * quty, this.scale);
    }

    public Quty multiply(double quty) {
        return new Quty(Math.round((double)this.value * quty), this.scale);
    }

    public Quty multiply(BigDecimal quty) {
        return this.multiply(quty, 4);
    }

    public Quty multiply(BigDecimal quty, int roundingMode) {
        long value = this.multiplyOp(quty, roundingMode);
        return new Quty(value, this.scale);
    }

    private long multiplyOp(BigDecimal quty, int roundingMode) {
        BigDecimal newValue = BigDecimal.valueOf(this.value).multiply(quty);
        return newValue.setScale(0, roundingMode).longValue();
    }

    public Quty multiplyBy(long quty) {
        this.value *= quty;
        return this;
    }

    public Quty multiplyBy(double quty) {
        this.value = Math.round((double)this.value * quty);
        return this;
    }

    public Quty multiplyBy(BigDecimal quty) {
        return this.multiplyBy(quty, 4);
    }

    public Quty multiplyBy(BigDecimal quty, int roundingMode) {
        this.value = this.multiplyOp(quty, roundingMode);
        return this;
    }

    private void assertSameQuty(Quty left, Quty right) {
        if(left.scale != right.scale) {
            throw new IllegalArgumentException("Quty scale mismatch.");
        }
    }
}