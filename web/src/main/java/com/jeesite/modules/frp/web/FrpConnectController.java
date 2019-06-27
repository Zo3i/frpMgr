/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.common.utils.ShellUtil;
import com.jeesite.modules.common.utils.WebHttpUtils;
import com.jeesite.modules.frp.entity.FrpConnect;
import com.jeesite.modules.frp.entity.FrpServer;
import com.jeesite.modules.frp.entity.Shell;
import com.jeesite.modules.frp.service.FrpServerService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * frp_serverController
 * @author jo
 * @version 2019-02-25
 */
@Controller
@RequestMapping(value = "${adminPath}/frp/frpConnect")
public class FrpConnectController extends BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private FrpServerService frpServerService;
	/**
	 * 查询列表
	 */
	@RequiresPermissions("frp:frpConnect:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<FrpServer> servers = frpServerService.findList(new FrpServer());
		Map<String, String> map = new HashMap<>();
		map.put("tcp", "tcp");
		map.put("udp", "udp");
		map.put("http", "http");
		map.put("https", "https");
		model.addAttribute("map", map);
		model.addAttribute("servers", servers);
		return "modules/frp/frpConnectList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("frp:frpConnect:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<FrpConnect> listData() {
		List<FrpConnect> list = new ArrayList<>();
//		String content = WebHttpUtils.get("http://47.88.169.121:7500/api/proxy/http",null, map);
//		JSONArray array = (JSONArray) JSONObject.parseObject(content).get("proxies");
//		for (Object i : array) {
//			FrpConnect s = JSONObject.parseObject(((JSONObject) i).toJSONString(), FrpConnect.class);
//			list.add(s);
//		}
		Page<FrpConnect> page =  new Page<FrpConnect>(1, 100, list.size(), list);
		return page;
	}


	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("frp:frpConnect:view")
	@RequestMapping(value = "list/{serverId}/{action}")
	@ResponseBody
	public List<FrpConnect> list(@PathVariable("serverId") String serverId,
								 @PathVariable("action") String action ) {
		FrpServer server = frpServerService.get(serverId);
		List<FrpConnect> list = new ArrayList<>();
		if (server != null) {
			Map map = new HashMap();
			map.put("Authorization", "Basic YWRtaW46YWRtaW4=");
			String content = WebHttpUtils.get("http://"+ server.getServerIp() +":7500/api/proxy/" + action,null, map);
			JSONArray array = (JSONArray) JSONObject.parseObject(content).get("proxies");
			for (Object i : array) {
				FrpConnect s = JSONObject.parseObject(((JSONObject) i).toJSONString(), FrpConnect.class);
				list.add(s);
			}
		}

		return list;
	}
}