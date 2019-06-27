package com.jeesite.modules.frp.entity;

public class FrpConnect {
    private String name;
    private String conf;
    private Double today_traffic_in;
    private Double today_traffic_out;
    private Integer cur_conns;
    private String last_start_time;
    private String last_close_time;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public Double getToday_traffic_in() {
        return today_traffic_in;
    }

    public void setToday_traffic_in(Double today_traffic_in) {
        this.today_traffic_in = today_traffic_in;
    }

    public Double getToday_traffic_out() {
        return today_traffic_out;
    }

    public void setToday_traffic_out(Double today_traffic_out) {
        this.today_traffic_out = today_traffic_out;
    }

    public Integer getCur_conns() {
        return cur_conns;
    }

    public void setCur_conns(Integer cur_conns) {
        this.cur_conns = cur_conns;
    }

    public String getLast_start_time() {
        return last_start_time;
    }

    public void setLast_start_time(String last_start_time) {
        this.last_start_time = last_start_time;
    }

    public String getLast_close_time() {
        return last_close_time;
    }

    public void setLast_close_time(String last_close_time) {
        this.last_close_time = last_close_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
