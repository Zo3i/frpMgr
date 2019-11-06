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
 * frpEntity
 * @author jo
 * @version 2019-02-25
 */
@Table(name="frp", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="project_name", attrName="projectName", label="项目名称", queryType=QueryType.LIKE),
		@Column(name="frp_domain_second", attrName="frpDomainSecond", label="二级域名"),
		@Column(name="frp_local_port", attrName="frpLocalPort", label="本地端口"),
		@Column(name="frp_remote_port", attrName="frpRemotePort", label="远程端口"),
		@Column(name="server_id", attrName="serverId", label="服务器"),
		@Column(name="user_id", attrName="userId", label="所属用户"),
		@Column(name="user", attrName="user", label="登录用户名"),
		@Column(name="password", attrName="password", label="登录密码"),
		@Column(name="type", attrName="type", label="类型"),
	}, orderBy="a.id DESC"
)
public class Frp extends DataEntity<Frp> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;		// 项目名称
	private String frpDomainSecond;		// 二级域名
	private String frpLocalPort;		// 本地端口
	private Long serverId;		// 服务器
	private String serverName;
	private String userId; // 当前用户
	private String site;
	private String frpRemotePort;// 远程端口
	private Integer type;
	private String user;
	private String password;

	
	public Frp() {
		this(null);
	}

	public Frp(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="项目名称长度不能超过 64 个字符")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=8, message="二级域名长度不能超过 8 个字符")
	public String getFrpDomainSecond() {
		return frpDomainSecond;
	}

	public void setFrpDomainSecond(String frpDomainSecond) {
		this.frpDomainSecond = frpDomainSecond;
	}
	
	@Length(min=0, max=6, message="本地端口长度不能超过 6 个字符")
	public String getFrpLocalPort() {
		return frpLocalPort;
	}

	public void setFrpLocalPort(String frpLocalPort) {
		this.frpLocalPort = frpLocalPort;
	}
	
	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
	public String getFrpRemotePort() {
		return frpRemotePort;
	}

	public void setFrpRemotePort(String frpRemotePort) {
		this.frpRemotePort = frpRemotePort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}