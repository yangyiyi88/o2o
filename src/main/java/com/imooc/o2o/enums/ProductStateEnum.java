package com.imooc.o2o.enums;

public enum ProductStateEnum {
    OFFLINE(-1,"非法商品"), DOWN(0,"下架"), SUCCESS(1,"操作成功"),
    INNER_ERROR(-1001,"操作失败"), EMPT(-1002,"商品为空");

    private int state;
    private String stateInfo;

    ProductStateEnum (int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 依据传入的state返回相应的enum值
     */
    public static ProductStateEnum stateOf (int state) {
        for (ProductStateEnum productStateEnum : values()) {
            if (productStateEnum.getState() == state) {
                return productStateEnum;
            }
        }
        return null;
    }

}
