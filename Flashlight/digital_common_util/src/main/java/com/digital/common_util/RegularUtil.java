package com.digital.common_util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fangzhengyou on 15/9/5.
 * 正则表达式验证工具类
 */
public class RegularUtil {
	/**
	 * 验证手机号的合法性
	 * 支持的手机号码段:13x,14x,15x,17x,18x,19x
	 * 合法 返回 true
	 * 不合法 返回 false
	 */
	public static boolean validatePhoneNum(String phone) {
		String regExp = "^[1][3,4,5,7,8]{1}[0-9]{1}[0-9]{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phone);
		return m.find();
	}
	
	/**
	 * 验证座机号码的合法性
	 * @param tel
	 * @return
	 */
	public static boolean isTel(String tel){
		String regExp = "^0[0-9]{2,3}-[2-8]{1}[0-9]{6,7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(tel);
		return m.find();
	}
	/**
	 * 验证用户名的合法性
	 * 合法 返回 true
	 * 不合法 返回 false
	 */
	public static boolean validateUserName(String userName) {
		String regExp = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？|\\s]";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(userName);
		return !m.find();
	}
	
	/**
	 * 验证String是否数字
	 * 合法 返回 true
	 * 不合法 返回 false
	 * @param value
	 * @return
	 */
	public static boolean IsNumber(String value){
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return value.matches(regex);  
    } 
	/**
	 * 验证String是否整数
	 * 合法 返回 true
	 * 不合法 返回 false
	 * @param value
	 * @return
	 */
	public static boolean IsInteger(String value){
		String regex = "^[1-9]\\d*|0$";
		return value.matches(regex);  
	} 
	
	/**
	 * 验证邮箱地址的合法性
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		String regExp = "^[0-9a-zA-Z_]+@[0-9a-zA-Z]+\\.[a-zA-Z]+$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(email);
		return m.find();
	}
}
