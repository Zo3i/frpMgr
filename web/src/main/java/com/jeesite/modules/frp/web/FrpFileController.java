/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.common.utils.JarFileUtil;
import com.jeesite.modules.common.utils.ZipUtils;
import com.jeesite.modules.frp.entity.Frp;
import com.jeesite.modules.frp.entity.FrpServer;
import com.jeesite.modules.frp.enums.ClientType;
import com.jeesite.modules.frp.service.FrpServerService;
import com.jeesite.modules.frp.service.FrpService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * frpController
 * @author jo
 * @version 2019-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/frp/frpFile")
public class FrpFileController extends BaseController {

	@Autowired
	private FrpService frpService;
	@Autowired
	private FrpServerService frpServerService;
	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Frp get(String id, boolean isNewRecord) {
		return frpService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("frp:frp:view")
	@RequestMapping(value = {"list", ""})
	public String list(Frp frp, Model model) {
		model.addAttribute("frp", frp);
		return "modules/frp/frpFileList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("frp:frp:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Frp> listData(Frp frp, HttpServletRequest request, HttpServletResponse response) {
		frp.setUserId(UserUtils.getUser().getId());
		Page<Frp> page = frpService.findPage(new Page<Frp>(request, response), frp);
		List<Frp> frpList = page.getList();
		if (frpList.size() > 0) {
			for (Frp s : frpList) {
				String site = "http://";
				FrpServer frpServer = frpServerService.get(String.valueOf(s.getServerId()));
				site += s.getFrpDomainSecond() + ".";
				site += frpServer.getSubdomainHost() + ":";
				site += s.getFrpRemotePort();
				site += "/file/";
				s.setSite(site);
			}
		}
		frpList = frpList.stream().filter(e -> e.getType() == ClientType.FILE.value).collect(Collectors.toList());
		page.setList(frpList);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("frp:frp:view")
	@RequestMapping(value = "form")
	public String form(Frp frp, Model model) {
		List<FrpServer> servers = frpServerService.findList(new FrpServer());
		model.addAttribute("frp", frp);
		model.addAttribute("servers", servers);
		return "modules/frp/frpFileForm";
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("frp:frp:view")
	@RequestMapping(value = "edit")
	public String edit(String id, Model model) {
		Frp frp = frpService.get(id);
		List<FrpServer> servers = frpServerService.findList(new FrpServer());
		model.addAttribute("frp", frp);
		model.addAttribute("servers", servers);
		return "modules/frp/frpFileForm";
	}


	/**
	 * 保存frp
	 */
	@RequiresPermissions("frp:frp:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Frp frp) {
		// 类型
		frp.setType(ClientType.FILE.value);
		//判断是否存在项目名称,二级域名,远程端口一样的;
		Frp isExist = frpService.isExist(frp.getProjectName(), frp.getFrpDomainSecond(), String.valueOf(frp.getServerId()), frp.getFrpRemotePort());
		if (isExist == null) {
			frp.setUserId(UserUtils.getUser().getId());
			frpService.save(frp);
		} else {
			return renderResult(Global.TRUE, text("项目名、二级域名冲突或远程端口冲突！"));
		}
		return renderResult(Global.TRUE, text("保存frp成功！"));
	}

	/**
	 * 更新frp
	 */
	@RequiresPermissions("frp:frp:edit")
	@PostMapping(value = "update")
	@ResponseBody
	public String update(@Validated Frp frp) {
		frpService.save(frp);
		return renderResult(Global.TRUE, text("保存frp成功！"));
	}
	
	/**
	 * 删除frp
	 */
	@RequiresPermissions("frp:frp:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Frp frp) {
		frpService.delete(frp);
		return renderResult(Global.TRUE, text("删除frp成功！"));
	}

	@RequestMapping("/exportFile/{id}")
	@ResponseBody
	public void exportFile(@PathVariable String id, HttpServletResponse response) throws IOException {
		Frp frp = frpService.get(id);
		FrpServer frpServer = frpServerService.get(String.valueOf(frp.getServerId()));

		//创建临时文件夹
		String zipName = UUID.randomUUID().toString();
		String dir = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator;
		String dir_client = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator + "client";
		File srcDir = new File(dir_client);
		log.info("临时文件夹准备");
		//拷贝到临时文件夹
		JarFileUtil.BatCopyFileFromJar("static/frp/frp-client-file", dir_client);
		log.info("拷贝到临时文件夹");
		// 创建文件夹
		File folder = new File(dir_client + File.separator + "file");
		folder.mkdir();
		//读取frpc.ini
		File temp_file = new File(dir_client + File.separator +"frpc.ini");
		StringBuffer res = new StringBuffer();
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader(temp_file));
		while ((line = reader.readLine()) != null) {
			res.append(line + "\n");
		}
		reader.close();
		log.info("读取文件");
		BufferedWriter writer = new BufferedWriter(new FileWriter(temp_file));
		String temp_string = res.toString();
		//替换模板
		String projectName = frp.getProjectName();
		String remotePort = frp.getFrpRemotePort();
		String token = frpServer.getAuthToken() == null ? "autn_token" : frpServer.getAuthToken();
		temp_string = temp_string.replaceAll("project_name", projectName);
		temp_string = temp_string.replaceAll("frp_remote_port", remotePort);
		temp_string = temp_string.replaceAll("frp_server_addr", frpServer.getServerIp());
		temp_string = temp_string.replaceAll("frp_auth_token", token);

		// 设置访问密码
		if (!StringUtils.isBlank(frp.getPassword())) {
			temp_string = temp_string.replaceAll("#plugin_http_user", "plugin_http_user=" + frp.getUser());
			temp_string = temp_string.replaceAll("#plugin_http_passwd", "plugin_http_passwd=" + frp.getPassword());
		}
		writer.write(temp_string);
		writer.flush();
		writer.close();
		log.info("替换模板");

		String zipFilePath = System.getProperty("java.io.tmpdir") + File.separator + zipName + "zip";
		ZipUtils.zip(dir, zipFilePath);
		File zipFile = new File(zipFilePath);
		// 以流的形式下载文件。
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(zipFile.getPath()));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		response.reset();
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + "client.zip");

		toClient.write(buffer);
		toClient.flush();
		toClient.close();
		FileUtils.deleteDirectory(srcDir);
		zipFile.delete();
		log.info("succeed");
	}

}