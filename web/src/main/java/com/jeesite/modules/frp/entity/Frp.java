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
 * @version 2019-02-23
 */
@Table(name="frp", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="project_name", attrName="projectName", label="项目名称", queryType=QueryType.LIKE),
		@Column(name="frp_domain_second", attrName="frpDomainSecond", label="二级域名"),
		@Column(name="frp_local_port", attrName="frpLocalPort", label="本地端口"),
	}, orderBy="a.id DESC"
)
public class Frp extends DataEntity<Frp> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;		// 项目名称
	private String frpDomainSecond;		// 二级域名
	private String frpLocalPort;		// 本地端口
	
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
	
}