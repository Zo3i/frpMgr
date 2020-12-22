package com.jeesite.modules.frp.web;

import com.jeesite.common.web.BaseController;
import com.jeesite.modules.common.utils.JarFileUtil;
import com.jeesite.modules.common.utils.ZipUtils;
import com.jeesite.modules.frp.entity.Frp;
import com.jeesite.modules.frp.entity.FrpServer;
import com.jeesite.modules.frp.enums.DownloadType;
import com.jeesite.modules.frp.service.FrpServerService;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.UUID;

@Controller
@RequestMapping(value = "${adminPath}/frp/bat")
public class FrpDownloadController extends BaseController {

	@Autowired
	private FrpServerService frpServerService;

	private Logger log =  LoggerFactory.getLogger(FrpDownloadController.class);

	@RequestMapping("/file/{id}/{type}")
	@ResponseBody
	public void exportFile(@PathVariable String id,
	                       @PathVariable DownloadType type,
	                       HttpServletResponse response) throws IOException {

		FrpServer server = frpServerService.get(id);

		//创建临时文件夹
		String zipName = UUID.randomUUID().toString();
		String copyPath = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator;
		File srcDir = new File(copyPath);
		//读取frpc.ini
		File temp_file = null;
		String filePath = "static/frp/bat/";
		boolean replace = false;
		switch (type.value) {
			case 1:
				replace = true;
				copyPath += "frpc_web.ini";
				filePath += "frpc_web.ini";
				break;
			case 2:
				replace = true;
				copyPath = filePath + "frpc_file_copy.ini";
				filePath += "frpc_file.ini";
				break;
			case 3:
				replace = true;
				copyPath = filePath + "frpc_rdp_copy.ini";
				filePath += "frpc_rdp.ini";
				break;
			case 4:
				copyPath += filePath + "frpc.exe";
				filePath += "frpc.exe";
				break;
			case 5:
				copyPath += filePath + "frpc_full.ini";
				filePath += "frpc_full.ini";
				break;
			default:
				break;
		}

		JarFileUtil.getCopyFileFromJar(filePath, copyPath);
		temp_file = new File(copyPath);

		if (replace) {
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
			String serverIp = server.getServerIp();
			String token = server.getAuthToken() == null ? "autn_token" : server.getAuthToken();
			temp_string = temp_string.replaceAll("FIX_NAME_HOST", serverIp);
			temp_string = temp_string.replaceAll("frp_auth_token", token);

			writer.write(temp_string);
			writer.flush();
			writer.close();
			log.info("替换模板");
		}


		// 以流的形式下载文件。
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(temp_file.getPath()));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		response.reset();
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + temp_file.getName());

		toClient.write(buffer);
		toClient.flush();
		toClient.close();
		FileUtils.deleteDirectory(srcDir);
		log.info("succeed");
	}


	@RequestMapping("/batDown/{id}")
	@ResponseBody
	public void bat(@PathVariable String id,
	                HttpServletRequest request,
	                HttpServletResponse response) throws IOException {


		FrpServer server = frpServerService.get(id);
		String url = "";
		url = request.getScheme() +"://" + request.getServerName()
				+ ":" +request.getServerPort()
				+ "/frp"
				+ request.getServletPath();
		url = url.replaceAll("batDown", "file");

		log.info(url);

		// 源文件目录
		String zipName = UUID.randomUUID().toString();
		String dir = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator;
		String dir_client = System.getProperty("java.io.tmpdir") + File.separator + zipName + File.separator + "bat_script";
		File srcDir = new File(dir_client);

		// 拷贝到临时文件夹
		// 下载main
		JarFileUtil.BatCopyFileFromJar("static/frp/bat_script", dir_client);
		// 下载lib
		JarFileUtil.BatCopyFileFromJar("static/frp/bat_script/lib", dir_client + File.separator + "lib");

		// 读取
		File temp_file = new File(dir_client + File.separator +"main.bat");
		StringBuffer res = new StringBuffer();
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader(temp_file));
		while ((line = reader.readLine()) != null) {
			res.append(line + "\r\n");
		}
		reader.close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(temp_file));
		String serverDomain = server.getSubdomainHost();
		String temp_string = res.toString();
		String ip = server.getServerIp();
		String token = server.getAuthToken() == null ? "autn_token" : server.getAuthToken();
		temp_string = temp_string.replaceAll("FIX_DOWNLOAD_URL", url);
		temp_string = temp_string.replaceAll("FIX_SERVER_DOMAIN", serverDomain);
		temp_string = temp_string.replaceAll("FIX_SERVER_IP", ip);
		temp_string = temp_string.replaceAll("frp_auth_token", token);
		//替换模板
		writer.write(temp_string);
		writer.flush();
		writer.close();

		String zipFilePath = System.getProperty("java.io.tmpdir") + File.separator + zipName + "zip";
		ZipUtils.zip(dir, zipFilePath);
		File zipFile = new File(zipFilePath);
		log.info("succeed");
		// 以流的形式下载文件。
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(zipFile.getPath()));
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		response.reset();
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		response.setHeader("content-disposition", "attachment;filename=" + "client.zip");

		toClient.write(buffer);
		toClient.flush();
		toClient.close();
		FileUtils.deleteDirectory(srcDir);
		zipFile.delete();
		log.info("succeed");
	}
}
