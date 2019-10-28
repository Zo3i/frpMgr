/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.jeesite.modules.common.utils.*;
import com.jeesite.modules.frp.entity.Frp;
import com.jeesite.modules.frp.entity.FrpConnect;
import com.jeesite.modules.frp.entity.Shell;
import com.sun.xml.bind.v2.TODO;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.frp.entity.FrpServer;
import com.jeesite.modules.frp.service.FrpServerService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * frp_serverController
 * @author jo
 * @version 2019-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/frp/frpServer")
public class FrpServerController extends BaseController {

	@Autowired
	private FrpServerService frpServerService;
	@Autowired
	private ShellUtil shellUtil;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public FrpServer get(String id, boolean isNewRecord) {
		return frpServerService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("frp:frpServer:view")
	@RequestMapping(value = {"list", ""})
	public String list(FrpServer frpServer, Model model) {
		model.addAttribute("frpServer", frpServer);
		return "modules/frp/frpServerList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("frp:frpServer:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FrpServer> listData(FrpServer frpServer, HttpServletRequest request, HttpServletResponse response) {
		Page<FrpServer> page = frpServerService.findPage(new Page<FrpServer>(request, response), frpServer); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("frp:frpServer:view")
	@RequestMapping(value = "form")
	public String form(FrpServer frpServer, Model model) {
		model.addAttribute("frpServer", frpServer);
		return "modules/frp/frpServerForm";
	}

	/**
	 * 保存frp_server
	 */
	@RequiresPermissions("frp:frpServer:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated FrpServer frpServer) {
		if (StringUtils.isBlank(frpServer.getWebPort())) {
			frpServer.setWebPort("8080");
		}
		if (StringUtils.isBlank(frpServer.getUserName())) {
			frpServer.setUserName("root");
		}
		frpServerService.save(frpServer);
		return renderResult(Global.TRUE, text("保存frp_server成功！"));
	}
	
	/**
	 * 删除frp_server
	 */
	@RequiresPermissions("frp:frpServer:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(FrpServer frpServer) {
		frpServerService.delete(frpServer);
		return renderResult(Global.TRUE, text("删除frp_server成功！"));
	}


	/**
	 * 远程安装FRP
	 */
	@RequiresPermissions("frp:frpServer:edit")
	@RequestMapping(value = "fastFrp/{id}")
	@ResponseBody
	public String fastFrp(@PathVariable("id") String id, @RequestParam("passwd") String passwd) throws Exception{
		FrpServer frpServer = frpServerService.get(id);
		if (frpServer != null) {
			passwd = AesUtil.decryptAES(passwd);
			Shell shell = new Shell(frpServer.getServerIp(), frpServer.getUserName(), passwd);
			try {
				shellUtil.execute(shell,"if [ ! -f \"fastFrp.sh\" ];then wget https://raw.githubusercontent.com/Zo3i/OCS/master/frp/fastFrp.sh; fi");
				shellUtil.execute(shell,"chmod 755 fastFrp.sh");
				shellUtil.execute(shell,"bash fastFrp.sh " + frpServer.getWebPort() + " " + frpServer.getSubdomainHost());
			} catch (RuntimeException e) {
				logger.info(e.toString().split(":")[1]);
				return e.toString().split(":")[1];
			}

			ArrayList<String> stdout = shell.getStandardOutput();

			for (String str : stdout) {
				logger.info(str);
			}
		}
		return "安装成功！";
	}


}