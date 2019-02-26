/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.frp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.common.utils.JarFileUtil;
import com.jeesite.modules.common.utils.ZipUtils;
import com.jeesite.modules.frp.entity.FrpServer;
import com.jeesite.modules.frp.service.FrpServerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.frp.entity.Frp;
import com.jeesite.modules.frp.service.FrpService;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * frpController
 * @author jo
 * @version 2019-02-23
 */
@Controller
@RequestMapping(value = "${adminPath}/frp")
public class FrpController extends BaseController {

	@Autowired
	private FrpService frpService;
	@Autowired
	private FrpServerService frpServerService;

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
		return "modules/frp/frpList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("frp:frp:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Frp> listData(Frp frp, HttpServletRequest request, HttpServletResponse response) {
		Page<Frp> page = frpService.findPage(new Page<Frp>(request, response), frp); 
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
		return "modules/frp/frpForm";
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
		return "modules/frp/frpForm";
	}


	/**
	 * 保存frp
	 */
	@RequiresPermissions("frp:frp:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Frp frp) {

		//判断是否存在项目名称一样的或存在二级域名一样的;
		Frp isExist = frpService.isExist(frp.getProjectName(), frp.getFrpDomainSecond());
		if (isExist == null) {
			frpService.save(frp);
		} else {
			return renderResult(Global.TRUE, text("项目名或二级域名冲突！"));
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

	@RequestMapping("/exportWin/{id}")
    @ResponseBody
    public void exportWin(@PathVariable String id, HttpServletResponse response) throws IOException {
        Frp frp = frpService.get(id);
        FrpServer frpServer = frpServerService.get(String.valueOf(frp.getServerId()));

        //创建临时文件夹
		String zipName = UUID.randomUUID().toString();
		String dir = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator;
		String dir_client = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator + "client";
		File srcDir = new File(dir_client);
		//拷贝到临时文件夹
		JarFileUtil.BatCopyFileFromJar("static/frp/frp-client", dir_client);

        //读取frpc.ini
        File temp_file = new File(dir_client + File.separator +"frpc.ini");
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
        String projectName = frp.getProjectName();
        String subdomain = frp.getFrpDomainSecond();
        String localport = frp.getFrpLocalPort();
        temp_string = temp_string.replaceAll("project_name", projectName);
        temp_string = temp_string.replaceAll("frp_subdomain", subdomain);
        temp_string = temp_string.replaceAll("frp_local_port", localport);
        temp_string = temp_string.replaceAll("frp_server_addr", frpServer.getServerIp());
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
           response.setHeader("Content-Disposition", "attachment;filename=" + "client.zip");


           toClient.write(buffer);
           toClient.flush();
           toClient.close();
           FileUtils.deleteDirectory(srcDir);
           zipFile.delete();
    }

    @RequestMapping("/exportMac/{id}")
    @ResponseBody
    public void exportMac(@PathVariable String id, HttpServletResponse response) throws IOException {
        Frp frp = frpService.get(id);
        FrpServer frpServer = frpServerService.get(String.valueOf(frp.getServerId()));

        // 源文件目录
		String zipName = UUID.randomUUID().toString();
		String dir = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator;
		String dir_client = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator + "client";
		File srcDir = new File(dir_client);

		//拷贝到临时文件夹
        JarFileUtil.BatCopyFileFromJar("static/frp/frp-client-mac", dir_client);

         //读取frpc.ini
        File temp_file = new File(dir_client + File.separator +"frpc.ini");
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
        String projectName = frp.getProjectName();
        String subdomain = frp.getFrpDomainSecond();
        String localport = frp.getFrpLocalPort();
        temp_string = temp_string.replaceAll("project_name", projectName);
        temp_string = temp_string.replaceAll("frp_subdomain", subdomain);
        temp_string = temp_string.replaceAll("frp_local_port", localport);
        temp_string = temp_string.replaceAll("frp_server_addr", frpServer.getServerIp());
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
           response.setHeader("Content-Disposition", "attachment;filename=" + "client.zip");

           toClient.write(buffer);
           toClient.flush();
           toClient.close();
           FileUtils.deleteDirectory(srcDir);
           zipFile.delete();
    }

}