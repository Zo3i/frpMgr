package com.jeesite.modules.frp.entity;

import java.util.ArrayList;

public class Shell {
    private String ip;
    private String password;
    private String username;
    private ArrayList<String> stdout;

    public Shell(final String ip, final String username, final String password) {
        this.ip = ip;
        this.username = username;
        this.password = password;
        stdout = new ArrayList<String>();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getStdout() {
        return stdout;
    }

    public void setStdout(ArrayList<String> stdout) {
        this.stdout = stdout;
    }

    /**
     * get stdout
     * @return
     */
    public ArrayList<String> getStandardOutput() {
        return stdout;
    }
}
