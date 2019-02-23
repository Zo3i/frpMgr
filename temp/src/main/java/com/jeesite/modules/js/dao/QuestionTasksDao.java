/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.QuestionTasks;

/**
 * js_question_tasksDAO接口
 * @author jo
 * @version 2018-11-02
 */
@MyBatisDao
public interface QuestionTasksDao extends CrudDao<QuestionTasks> {
	
}