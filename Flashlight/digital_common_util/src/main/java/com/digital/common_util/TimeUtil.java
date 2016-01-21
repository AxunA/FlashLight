package com.digital.common_util;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by fangzhengyou on 15/9/5.
 * 各种时间处理工具类
 */
public class TimeUtil {
	/**
	 * 获取当前时间
	 */
	public static String getCurTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate=new Date(System.currentTimeMillis());//获取当前时间
		String str=formatter.format(curDate);
		return str;
	}

	//转化秒数到00:00:00的显示
	public static String secondsToTimeFormat(int totalTime){
		int hour=totalTime/(60*60);
		int minutes=totalTime/60-hour*60;
		int seconds=totalTime-hour*60*60-minutes*60;

		return toTimeFormat(hour, minutes, seconds);
	}

	//将hour/minutes/seconds到00:00:00的显示
	public static String toTimeFormat(int hours,int minutes,int seconds){
		String str;
		if(hours<=0){
			 str= String.format("%02d:%02d", minutes, seconds);
		}else {
			 str= String.format("%02d:%02d:%02d", hours, minutes, seconds);
		}
		return str;
	}

	//将String类型的hour/minutes/seconds转化为秒数
	public static int toSeconds(String hour, String minutes, String seconds){
		int pHour= Integer.valueOf(hour);
		int pMinutes= Integer.valueOf(minutes);
		int pSeconds= Integer.valueOf(seconds);

		int pTime=pHour*60*60+pMinutes*60+pSeconds;
		return pTime;
	}

	//转 2015-10-1 00:00:00到 2015-10-1
	public static String toData(String time){
		int blank=time.indexOf(" ");
		time=time.substring(0,blank);
		return time;
	}

}
