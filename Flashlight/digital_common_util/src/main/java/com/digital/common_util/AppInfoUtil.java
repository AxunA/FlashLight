package com.digital.common_util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by fangzhengyou on 15/9/5.
 * 获取应用信息工具类
 */
public class AppInfoUtil {

	/**
	 * 获取版本号version code
	 */
	public static String getVersionCode(Context context) {
		String verison="";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = String.valueOf(info.versionCode);
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return verison;
		}
	}
	
	/**
	 * 获取版本名 version name
	 */
	public static String getVersionName(Context context) {
		String version="";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return version;
		}
	}
	
	/**
	 * 判断Activity是否在栈顶
	 * @return true or false
	 */
	public static boolean isForeground(String PackageName,String activityName,Context context){
		  ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		  List< ActivityManager.RunningTaskInfo > task = manager.getRunningTasks(1);
		  ComponentName componentInfo = task.get(0).topActivity;
		  if(componentInfo.toString().equals(activityName)) return true;
		  return false;
	}
	
	/**
	 * 获得app图标
	 * @param ctx
	 * @param pkg
	 * @return
	 */
	public static Drawable getIcon(Context ctx,String pkg){
		try{
			PackageInfo info = ctx.getPackageManager().getPackageInfo(pkg,0);
			Drawable d = info.applicationInfo.loadIcon(ctx.getPackageManager());
			return d;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断进程该Activity是否已经运行
	 * @param context
	 * @return
	 */
	public static boolean rmActivityIfOpened(Context context,Activity activity){
		/*try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_ACTIVITIES);
			ActivityInfo[] activitiesInfo=pi.activities;

			String curActivityName = activity.getClass().getName();
			for (int i = 0; i < activitiesInfo.length; ++i) {

				if (curActivityName.equals(activitiesInfo[i].parentActivityName)) {
					pi.remove(i);
					break;
				}
			}
		} catch (PackageManager.NameNotFoundException e) {
			// Do nothing. Adapter will be empty.
		}*/
		return false;
	}

	public static boolean isVersionFirstLaunch(Context context,String comSharePre){
		final String VERSION_CODE="version_code";
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		int currentVersion = info.versionCode;
		SharedPreferences prefs = context.getSharedPreferences(comSharePre, Context.MODE_PRIVATE);
		int lastVersion = prefs.getInt(VERSION_CODE, 0);
		if (currentVersion > lastVersion) {
			//如果当前版本大于上次版本，该版本属于第一次启动
			//将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
			prefs.edit().putInt(VERSION_CODE,currentVersion).commit();
			return true;
		}else{
			return false;
		}
	}
	
}
