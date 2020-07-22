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
import org.springframework.web.bind.annotation.ResponseBody;

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
		//读取frpc.ini
		File temp_file = null;
		String filePath = "static/frp/bat/";
		String copyPath = "";
		boolean replace = false;
		switch (type.value) {
			case 1:
				replace = true;
				copyPath = filePath + "frpc_web_copy.ini";
				filePath += "frpc_web.ini";
				JarFileUtil.getCopyFileFromJar(filePath, copyPath);
				break;
			case 2:
				replace = true;
				copyPath = filePath + "frpc_file_copy.ini";
				filePath += "frpc_file.ini";
				JarFileUtil.getCopyFileFromJar(filePath, copyPath);
				break;
			case 3:
				replace = true;
				copyPath = filePath + "frpc_rpc_copy.ini";
				filePath += "frpc_rpc.ini";
				JarFileUtil.getCopyFileFromJar(filePath, copyPath);
				break;
			case 4:
				copyPath = filePath + "frpc.exe";
				filePath = "frpc.exe";
				break;
			case 5:
				copyPath = filePath + "frpc_full.ini";
				filePath = "frpc_full.ini";
				break;
			default:
				break;
		}

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
			temp_string = temp_string.replaceAll("FIX_NAME_HOST", serverIp);
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
		FileUtils.forceDelete(temp_file);
		log.info("succeed");
	}
}
