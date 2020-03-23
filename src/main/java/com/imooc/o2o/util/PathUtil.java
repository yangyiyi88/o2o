package com.imooc.o2o.util;

public class PathUtil {
    public static String separator = System.getProperty("file.separator");

    public static String getImageBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if(basePath.toLowerCase().startsWith("win")){
            basePath = "D:/yangkun/Pictures/image";
        }else {
            basePath = "/Users/yangkun/Pictures/image";
        }
        return basePath.replace("/", separator);
    }

    public static String getShopImagePath(Long shopId){
        String imagePath = "/upload/shop/" + shopId +"/";
        return imagePath.replace("/", separator);
    }
}
