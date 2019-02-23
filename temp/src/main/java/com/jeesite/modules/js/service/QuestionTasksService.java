/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.js.entity.QuestionTasks;
import com.jeesite.modules.js.dao.QuestionTasksDao;

/**
 * js_question_tasksService
 * @author jo
 * @version 2018-11-02
 */
@Service
@Transactional(readOnly=true)
public class QuestionTasksService extends CrudService<QuestionTasksDao, QuestionTasks> {
	
	/**
	 * 获取单条数据
	 * @param questionTasks
	 * @return
	 */
	@Override
	public QuestionTasks get(QuestionTasks questionTasks) {
		return super.get(questionTasks);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param questionTasks
	 * @return
	 */
	@Override
	public Page<QuestionTasks> findPage(Page<QuestionTasks> page, QuestionTasks questionTasks) {
		return super.findPage(page, questionTasks);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param questionTasks
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(QuestionTasks questionTasks) {
		super.save(questionTasks);
	}
	
	/**
	 * 更新状态
	 * @param questionTasks
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(QuestionTasks questionTasks) {
		super.updateStatus(questionTasks);
	}
	
	/**
	 * 删除数据
	 * @param questionTasks
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(QuestionTasks questionTasks) {
		super.delete(questionTasks);
	}
	
}