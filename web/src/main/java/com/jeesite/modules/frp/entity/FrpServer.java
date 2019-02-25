/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * frp_serverEntity
 * @author jo
 * @version 2019-02-25
 */
@Table(name="frp_server", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="dashboard_port", attrName="dashboardPort", label="FRP面板端口"),
		@Column(name="dashboard_user", attrName="dashboardUser", label="FRP面板账户"),
		@Column(name="dashboard_pwd", attrName="dashboardPwd", label="FRP面板密码"),
		@Column(name="subdomain_host", attrName="subdomainHost", label="域名"),
		@Column(name="server_ip", attrName="serverIp", label="服务器ip"),
		@Column(name="web_port", attrName="webPort", label="web端口"),
		@Column(name="server_name", attrName="serverName", label="服务器名称", queryType=QueryType.LIKE),
	}, orderBy="a.id DESC"
)
public class FrpServer extends DataEntity<FrpServer> {
	
	private static final long serialVersionUID = 1L;
	private String dashboardPort;		// FRP面板端口
	private String dashboardUser;		// FRP面板账户
	private String dashboardPwd;		// FRP面板密码
	private String subdomainHost;		// 域名
	private String serverIp;		// 服务器ip
	private String serverName;		// 服务器名称
	private String webPort; //web端口
	
	public FrpServer() {
		this(null);
	}

	public FrpServer(String id){
		super(id);
	}
	
	@Length(min=0, max=6, message="FRP面板端口长度不能超过 6 个字符")
	public String getDashboardPort() {
		return dashboardPort;
	}

	public void setDashboardPort(String dashboardPort) {
		this.dashboardPort = dashboardPort;
	}
	
	@Length(min=0, max=32, message="FRP面板账户长度不能超过 32 个字符")
	public String getDashboardUser() {
		return dashboardUser;
	}

	public void setDashboardUser(String dashboardUser) {
		this.dashboardUser = dashboardUser;
	}
	
	@Length(min=0, max=128, message="FRP面板密码长度不能超过 128 个字符")
	public String getDashboardPwd() {
		return dashboardPwd;
	}

	public void setDashboardPwd(String dashboardPwd) {
		this.dashboardPwd = dashboardPwd;
	}
	
	@Length(min=0, max=16, message="域名长度不能超过 16 个字符")
	public String getSubdomainHost() {
		return subdomainHost;
	}

	public void setSubdomainHost(String subdomainHost) {
		this.subdomainHost = subdomainHost;
	}
	
	@Length(min=0, max=20, message="服务器ip长度不能超过 20 个字符")
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	
	@Length(min=0, max=255, message="服务器名称长度不能超过 255 个字符")
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getWebPort() {
		return webPort;
	}

	public void setWebPort(String webPort) {
		this.webPort = webPort;
	}
}