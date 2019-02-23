/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.frp.entity.Frp;
import com.jeesite.modules.frp.dao.FrpDao;

/**
 * frpService
 * @author jo
 * @version 2019-02-23
 */
@Service
@Transactional(readOnly=true)
public class FrpService extends CrudService<FrpDao, Frp> {

	@Autowired
	FrpDao frpDao;
	
	/**
	 * 获取单条数据
	 * @param frp
	 * @return
	 */
	@Override
	public Frp get(Frp frp) {
		return super.get(frp);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param frp
	 * @return
	 */
	@Override
	public Page<Frp> findPage(Page<Frp> page, Frp frp) {
		return super.findPage(page, frp);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param frp
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Frp frp) {
		super.save(frp);
	}
	
	/**
	 * 更新状态
	 * @param frp
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Frp frp) {
		super.updateStatus(frp);
	}
	
	/**
	 * 删除数据
	 * @param frp
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Frp frp) {
		super.delete(frp);
	}

	@Transactional(readOnly=false)
	public Frp isExist(String proName, String domain) {
		return frpDao.isExist(proName, domain);
	};
	
}