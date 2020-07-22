package com.jeesite.modules.frp.enums;

public enum DownloadType {

    WEB(1, "web穿透"),
    FILE(2, "目录文件"),
    RDP(3, "远程登录"),
    EXE(4, "exe主程序"),
    FULL_INI(5, "Full_ini");


    public final Integer value;
    public final String desc;

    private DownloadType(Integer value, String desc) {
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
