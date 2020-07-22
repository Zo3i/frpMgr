package com.jeesite.modules.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;

public class JarFileUtil {
    public static void BatCopyFileFromJar(String path,String newpath) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //获取所有匹配的文件
            Resource[] resources = resolver.getResources(path+"/*");
            for(int i=0;i<resources.length;i++) {
                Resource resource=resources[i];
                org.apache.commons.io.FileUtils.copyInputStreamToFile(resource.getInputStream(), new File(newpath + File.separator + resource.getFilename()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getCopyFileFromJar(String path, String newpath) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource(path);
        try {
            FileUtils.copyInputStreamToFile(resource.getInputStream(), new File(newpath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

       public static void write2File(InputStream is, String filePath) throws IOException {
        OutputStream os = new FileOutputStream(filePath);
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((len = is.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, len);
        }
        os.close();
        is.close();
    }

        public static boolean makeFile(String path) {
        File file = new File(path);
        if(file.exists()) {
            return false;
        }
        if (path.endsWith(File.separator)) {
            return false;
        }
        if(!file.getParentFile().exists()) {
            if(!file.getParentFile().mkdirs()) {
                return false;
            }
        }
        try {
            if (file.createNewFile()) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
