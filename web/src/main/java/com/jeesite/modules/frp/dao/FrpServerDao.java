/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.frp.entity.FrpServer;

/**
 * frp_serverDAO接口
 * @author jo
 * @version 2019-02-25
 */
@MyBatisDao
public interface FrpServerDao extends CrudDao<FrpServer> {
	
}