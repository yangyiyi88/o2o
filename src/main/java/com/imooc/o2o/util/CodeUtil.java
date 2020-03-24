package com.imooc.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
        //从验证码图片中获取验证码
        String verifyCodeException = (String)request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        //判断两者是否相同
        if(verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeException)){
            return false;
        }
        return true;
    }
}

