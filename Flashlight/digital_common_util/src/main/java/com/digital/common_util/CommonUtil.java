package com.digital.common_util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by fangzhengyou on 15/9/5.
 * 代码集合工具类
 * 主要将公用的代码集结在这里
 */
public class CommonUtil {

    // 可逆的加密算法
    public static String encryption(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 'p');
        }
        String s = new String(a);
        return s;
    }

    // 加密后解密
    public static String decode(String inStr) {
        if(inStr==null)return null;
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 'p');
        }
        String k = new String(a);
        return k;
    }

    /**
     * 获取当前时间
     */
    public static String getCurTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间
        String str=formatter.format(curDate);
        return str;
    }

    /**
     * 拨打电话
     * @param phoneNum
     * @param mContext
     */
    public static void call(String phoneNum,Context mContext){
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        mContext.startActivity(it);
    }

    public static int getScreenHeight(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenWidth(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 根据ListView的Item来确定它的高度
     * @param listView
     *
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int itemHeight = 0;
        for ( int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高,需要注意Item的Layout必须是LinearLayout
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            itemHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = itemHeight+ (listView.getDividerHeight() *(listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

}