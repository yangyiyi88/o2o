package com.imooc.o2o.enums;

public enum LocalAuthStateEnum {
    LOGINFAIL(-1, "密码或账号输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006, "注册信息为空"),

    ONLY_ONE_ACCOUNT(-1007,"最多只能绑定一个本地账号");

    private int state;
    private String stateInfo;

    LocalAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 依据传入的state返回相应的enum值
     * @param state
     * @return
     */
    public static LocalAuthStateEnum stateOf (int state) {
        for (LocalAuthStateEnum localAuthStateEnum : values()) {
            if (localAuthStateEnum.getState() == state) {
                return localAuthStateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
