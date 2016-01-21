package com.digital.common_util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fangzhengyou on 15/9/5.
 * SharePreference工具类
 */
public class SharePreferenceUtil {

	public static String sharePreference="sharedPreferences";
	/**
	 * 获取值
	 */
	public static String getValue(Context context,String key,String defaultValue) {
		SharedPreferences share =context.getSharedPreferences(sharePreference, context.MODE_PRIVATE);
		String result=share.getString(key, null);
		if(result==null)return defaultValue;
		return result;
	}

	/**
	 * 写入值
	 */
	public static void setValue(Context context,String key,String value) {
		SharedPreferences share =context.getSharedPreferences(sharePreference, context.MODE_PRIVATE);
		SharedPreferences.Editor edit= share.edit();
		edit.putString(key, value);
		edit.commit();
	}

}
