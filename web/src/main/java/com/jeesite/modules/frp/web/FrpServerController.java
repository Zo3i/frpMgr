/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.common.utils.JarFileUtil;
import com.jeesite.modules.common.utils.ZipUtils;
import com.jeesite.modules.frp.entity.Frp;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
		if (StringUtils.isBlank(frpServer.getDashboardPort())) {
			frpServer.setDashboardPort("7000");
		}
		if (StringUtils.isBlank(frpServer.getDashboardUser())) {
			frpServer.setDashboardUser("admin");
		}
		if (StringUtils.isBlank(frpServer.getDashboardPwd())) {
			frpServer.setDashboardPwd("admin");
		}
		if (StringUtils.isBlank(frpServer.getWebPort())) {
			frpServer.setWebPort("8080");
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

	@RequestMapping("/exportServer/{id}")
    @ResponseBody
    public void exportServer(@PathVariable String id, HttpServletResponse response) throws IOException {

		FrpServer frpServer = frpServerService.get(id);

        // 源文件目录
		String zipName = UUID.randomUUID().toString();
		String dir = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator;
		String dir_client = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator + "server";
		File srcDir = new File(dir_client);

		//拷贝到临时文件夹
        JarFileUtil.BatCopyFileFromJar("static/frp/server", dir_client);

         //读取frpc.ini
        File temp_file = new File(dir_client + File.separator +"frps.ini");
        StringBuffer res = new StringBuffer();
        String line = null;
		BufferedReader reader = new BufferedReader(new FileReader(temp_file));
        while ((line = reader.readLine()) != null) {
		    res.append(line + "\n");
		}
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(temp_file));
        String temp_string = res.toString();
        //替换模板
		String webPort = frpServer.getWebPort();
		String dashboardPort = frpServer.getDashboardPort();		// FRP面板端口
		String dashboardUser = frpServer.getDashboardUser();		// FRP面板账户
		String dashboardPwd = frpServer.getDashboardPwd();		// FRP面板密码
		String subdomainHost = frpServer.getSubdomainHost();		// 域名

        temp_string = temp_string.replaceAll("frp_vhost_http_port", webPort);
        temp_string = temp_string.replaceAll("frp_dashboard_port", dashboardPort);
        temp_string = temp_string.replaceAll("frp_dashboard_user", dashboardUser);
        temp_string = temp_string.replaceAll("frp_dashboard_pwd", dashboardPwd);
		temp_string = temp_string.replaceAll("frp_subdomain_host", subdomainHost);

        writer.write(temp_string);
		writer.flush();
		writer.close();

        String zipFilePath = System.getProperty("java.io.tmpdir") + File.separator + zipName + "zip";
        ZipUtils.zip(dir, zipFilePath);
        File zipFile = new File(zipFilePath);
	        System.out.println("succeed");
           // 以流的形式下载文件。
           BufferedInputStream fis = new BufferedInputStream(new FileInputStream(zipFile.getPath()));
           byte[] buffer = new byte[fis.available()];
           fis.read(buffer);
           fis.close();
           response.reset();
           OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
           response.setContentType("application/octet-stream");
           response.setHeader("Content-Disposition", "attachment;filename=" + "server.zip");

           toClient.write(buffer);
           toClient.flush();
           toClient.close();
           FileUtils.deleteDirectory(srcDir);
           zipFile.delete();
    }
	
}