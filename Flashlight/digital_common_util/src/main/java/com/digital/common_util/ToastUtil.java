package com.digital.common_util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


/**
 * Created by fangzhengyou on 15/9/5.
 * Toast显示工具类
 */
public class ToastUtil {

	public static final int ONE_SECOND = 1 * 1000;
	public static final int TWO_SECOND = 2 * 1000;
	public static final int THREE_SECOND = 3 * 1000;
	public static final int TEN_SECOND = 10 * 1000;

	public static void showText(final Context context, final String msg,final int duration) {

		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (null == context)return;
				Toast.makeText(context, msg, duration).show();
			}
		});
	}

	public static void showText(final Context context, final int resId,final int duration) {
		String msg=context.getResources().getString(resId);
		showText(context,msg,duration);
	}
}
