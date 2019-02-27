/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.frp.entity.Frp;
import org.apache.ibatis.annotations.Param;

/**
 * frpDAO接口
 * @author jo
 * @version 2019-02-23
 */
@MyBatisDao
public interface FrpDao extends CrudDao<Frp> {
	Frp isExist(@Param("proName") String proName, @Param("domain") String domain, @Param("serverId") String serverId);
}