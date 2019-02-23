/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.test;

import com.jeesite.modules.common.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.jeesite.modules.config.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * 初始化核心表数据
 * @author ThinkGem
 * @version 2017-10-22
 */
@ActiveProfiles("test")
@SpringBootTest(classes=Application.class)
@Rollback(false)
public class InitCoreData extends com.jeesite.modules.sys.db.InitCoreData {
	
	@Test
	public void initCoreData() throws Exception{
		createTable();
		initLog();
		initArea();
		initConfig();
		initModule();
		initDict();
		initRole();
		initMenu();
		initUser();
		initOffice();
		initCompany();
		initPost();
		initEmpUser();
		initMsgPushJob();
		initGenTestData();
		initGenTreeData();
	}

	public static void main(String[] args) {
	    String host = "https://smsapi.api51.cn";
	    String path = "/code/";
	    String method = "GET";
	    String appcode = "1e2e2a3e59cc432587818a3eb1b2f219";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("code", "1234");
	    querys.put("mobile", "17605002448");


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	//System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
}
