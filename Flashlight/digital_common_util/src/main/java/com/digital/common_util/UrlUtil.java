
package com.digital.common_util;

/**
 * Created by fangzhengyou on 15/9/5.
 * Log显示工具类
 */
public class UrlUtil {

    public static String addParamToUrl(String curUrl,String param){
        String resultUrl;
        if(curUrl==null)return "";
        if (curUrl.endsWith("html")) {
            resultUrl = curUrl + "?"+param;
        } else {
            resultUrl = curUrl + "&"+param;
        }
        return resultUrl;
    }

}
