package com.digital.common_util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fangzhengyou on 15/9/5.
 * MD5加密工具类
 */
public class MD5Util {
	 // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


    public static String md5Signature(TreeMap<String, String> params,String secret) {
        String result = null;
        StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
        if (orgin == null) return result;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(orgin.toString().getBytes("utf-8")));
        } catch (Exception e) {
            throw new java.lang.RuntimeException("sign error !");
        }
        return result;
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 添加参数的封装方法
     * 由Key头字母排序，后面跟Value，最后加secret
     * 因为TreeMap会自动排序
     *
     * @param params
     * @return
     */
    private static StringBuffer getBeforeSign(TreeMap<String, String> params, StringBuffer secret) {
        if (params == null) return null;
        StringBuffer stringBuffer=new StringBuffer();
        Map<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.putAll(params);
        Iterator<String> iter = treeMap.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            stringBuffer.append(name).append(params.get(name));
        }
        stringBuffer.append(secret);
        return stringBuffer;
    }


}
