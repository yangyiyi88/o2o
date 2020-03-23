package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String key){
        if(key != null && !key.equals("")){
            return Integer.decode(request.getParameter(key));
        }else {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key){
        if(key != null && !key.equals("")){
            return Long.valueOf(request.getParameter(key));
        }else {
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key){
        if(key != null && !key.equals("")){
            return Double.valueOf(request.getParameter(key));
        }else {
            return -1D;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request, String key){
        if(key != null && !key.equals("")){
            return Boolean.valueOf(request.getParameter(key));
        }else {
            return false;
        }
    }

    public static String  getString(HttpServletRequest request, String key){
        if(key != null && !key.equals("")){
            String value= request.getParameter(key);
            if (value != null && !value.equals("")){
                return value.trim();
            }
        }
        return null;
    }
}
