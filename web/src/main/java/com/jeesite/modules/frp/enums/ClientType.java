package com.jeesite.modules.frp.enums;

public enum ClientType {

    WEB(1, "web穿透"),
    SSH(2, "ssh登录"),
    RDP(3, "远程登录");

    public final Integer value;
    public final String desc;

    private ClientType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
