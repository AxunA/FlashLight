package com.digital.common_util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;


/**
 * Created by fangzhengyou on 15/9/7.
 * 系统信息获取工具类
 */
public class SystemUtil {
    /**
     * 获取机器MAC地址
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    public static String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 检查是否连接网络
     * 有Toast
     * @param context
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取屏幕高度
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * 获取屏幕宽度
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 添加应用到桌面快捷方式
     * @param ctx
     * @param name
     * @param icon
     * @param duplicate
     * @param intent
     */
    public static void addSelfShortcut(Context ctx,String name,Parcelable icon,boolean duplicate,Intent intent) {
        Intent shortcut = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 显示的名字
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,name);
        // 显示的图标
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 不允许重复创建
        shortcut.putExtra("duplicate", duplicate);
        // 这个是快捷方式所实现的功能
        intent.setAction(Intent.ACTION_MAIN);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        // 发送广播用以创建shortcut
        ctx.sendBroadcast(shortcut);
    }

    /**
     * 添加应用到桌面快捷方式
     * @param ctx
     * @param nameId
     * @param iconId
     * @param duplicate
     * @param intent
     */
    public static void addSelfShortcut(Context ctx,int nameId,int iconId,boolean duplicate,Intent intent) {
        String name=ctx.getString(nameId);
        Parcelable icon = Intent.ShortcutIconResource.fromContext(ctx,iconId);
        addSelfShortcut(ctx, name, icon, duplicate, intent);
    }

    public static void setCursorToEnd(EditText editText)
    {
        if(editText != null)
        {
            CharSequence text = editText.getText();
            int cursorPos = text.length();
            editText.setSelection(cursorPos, cursorPos);
        }
    }


}
