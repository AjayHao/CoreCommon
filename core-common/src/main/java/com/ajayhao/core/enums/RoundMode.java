package com.ajayhao.core.enums;

/**
 * 舍入模式<br/>
 */
public enum RoundMode {
    ROUND_UP(0), ROUND_DOWN(1), ROUND_CEILING(2), ROUND_FLOOR(3), ROUND_HALF_UP(4), ROUND_HALF_DOWN(5), ROUND_HALF_EVEN(6), ROUND_UNNECESSARY(7);

    int value = -1;

    RoundMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
