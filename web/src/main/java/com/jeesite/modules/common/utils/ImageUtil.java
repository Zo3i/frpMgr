package com.jeesite.modules.common.utils;

public class ImageUtil {

    private static String[] IMAGE_FILE_EXT = new String[] {"bmp", "jpg", "jpeg", "png", "gif"};

    public static String IMAGE_DIR = "C:/Users/Administrator/Desktop/Pro/jsMgr/web/src/main/resources/static/image/";

    public static String IMAGE_DOMAIN = "http://localhost:8090/frp/a/image/";

    public static boolean isImageAllowed(String imageExt) {
        for (String ext : IMAGE_FILE_EXT) {
            if (ext.equals(imageExt)) {
                return true;
            }
        }
        return false;
    }
}