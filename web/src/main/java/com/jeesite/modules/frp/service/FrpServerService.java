/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.frp.entity.FrpServer;
import com.jeesite.modules.frp.dao.FrpServerDao;

/**
 * frp_serverService
 * @author jo
 * @version 2019-02-25
 */
@Service
@Transactional(readOnly=true)
public class FrpServerService extends CrudService<FrpServerDao, FrpServer> {
	
	/**
	 * 获取单条数据
	 * @param frpServer
	 * @return
	 */
	@Override
	public FrpServer get(FrpServer frpServer) {
		return super.get(frpServer);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param frpServer
	 * @return
	 */
	@Override
	public Page<FrpServer> findPage(Page<FrpServer> page, FrpServer frpServer) {
		return super.findPage(page, frpServer);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param frpServer
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FrpServer frpServer) {
		super.save(frpServer);
	}
	
	/**
	 * 更新状态
	 * @param frpServer
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FrpServer frpServer) {
		super.updateStatus(frpServer);
	}
	
	/**
	 * 删除数据
	 * @param frpServer
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FrpServer frpServer) {
		super.delete(frpServer);
	}

}