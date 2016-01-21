package com.digital.common_util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by fangzhengyou on 15/9/5.
 * Bitmap的应用工具
 *
 * 1、Bitmap和Drawable互相转换
 * 2、Bitmap和字节数组互相转换
 * 3、对Bitmap的操作
 */
public class BitmapUtil {
	
	/**
	 * Bitmap转换成Drawable
	 * @param context
	 * @param bmp
	 * @return
	 */
	public static BitmapDrawable toDrawable(Context context,Bitmap bmp){
		BitmapDrawable bd= new BitmapDrawable(context.getResources(), bmp);
		return bd;
	}
	
	/**
	 * Drawable转Bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap toBitmap(Drawable drawable){
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		return bm;
	}
	
	/**
	 * 从资源中获取Bitmap
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap getBitmapFromResources(Context context, int resId) {
		Resources res = context.getResources();
		return BitmapFactory.decodeResource(res, resId);
	}
	
	
	/**
	 * byte[]转换成Bitmap
	 * @param data
	 * @return
	 */
	public static Bitmap toBitmap(byte[] data){
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
	
	/**
	 * Bitmap转换成byte[]
	 * @param bmp
	 * @return
	 */
	public static byte[] toBytes(Bitmap bmp){
		// Default size is 32 bytes
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			bmp.compress(CompressFormat.JPEG, 100, bos);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bos.toByteArray();
	}

	/**
	 * Bitmap缩放
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();  
        int h = bitmap.getHeight();  
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);  
        float scaleHeight = ((float) height / h);  
        matrix.postScale(scaleWidth, scaleHeight);  
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;  
    }
    
    /**
     * 将Drawable转化为Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();  
        int h = drawable.getIntrinsicHeight();  
  
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565;
        // 建立对应 bitmap  
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布  
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);  
        drawable.draw(canvas);  
        return bitmap;  
    } 
    
    /**
     * 获得圆角图片
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();  
        int h = bitmap.getHeight();  
        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;  
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);  
        return output;  
    }
    
    /**
     * 获得带倒影的图片
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;  
        int w = bitmap.getWidth();  
        int h = bitmap.getHeight();  
      
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);  
      
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);
      
        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);
      
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);  
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);  
      
        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);  
      
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,  
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);  
        // Set the Transfer mode to be porter duff and destination in  
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient  
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()  
                + reflectionGap, paint);  
      
        return bitmapWithReflection;  
    }
    
    /**
     * Drawable缩放
     * @param drawable
     * @param w
     * @param h
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();  
        int height = drawable.getIntrinsicHeight();  
        // drawable转换成bitmap  
        Bitmap oldbmp = drawableToBitmap(drawable);
        // 创建操作图片用的Matrix对象  
        Matrix matrix = new Matrix();
        // 计算缩放比例  
        float sx = ((float) w / width);  
        float sy = ((float) h / height);  
        // 设置缩放比例  
        matrix.postScale(sx, sy);  
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图  
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
        return new BitmapDrawable(newbmp);
    }  
    
    public static String getBase64FromPath(String path)
 	{
 		String base64="";
 		try
 		{
 			File file = new File(path);
 			byte[] buffer = new byte[(int) file.length() + 100];  
			@SuppressWarnings("resource")
			int length = new FileInputStream(file).read(buffer);
 	        base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
 		}
 		catch (IOException e) {
			e.printStackTrace();
		}
 		return base64;
 	}
    
	/**
	 * 缩放图片
	 * @param bm
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
 	public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight)
 	{ 
  	   int width = bm.getWidth();
  	   int height = bm.getHeight();
  	   float scaleWidth = ((float) newWidth) / width;
  	   float scaleHeight = ((float) newHeight) / height;
  	   Matrix matrix = new Matrix();
  	   matrix.postScale(scaleWidth, scaleHeight);
  	   Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
  	   return newbm;
 	}

 	
 	/**
 	 * 获取本地的Bitmap，并设置大小
 	 * @param path
 	 * @param w
 	 * @param h
 	 * @return
 	 */
 	public static Bitmap getBitmapFromPath(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Config.ARGB_8888;
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int)scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}
 	
 	public static String getBase64FromBitmap(Bitmap bitmap, int bitmapQuality)
 	{
 		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
 		bitmap.compress(CompressFormat.PNG, bitmapQuality, bStream);
 		byte[] bytes = bStream.toByteArray();
 		return Base64.encodeToString(bytes, Base64.DEFAULT);
 	}
 	
 	public static Bitmap getBitmapFromBase64(String string)
 	{
 		byte[] bitmapArray = null;
 		try {
 		bitmapArray = Base64.decode(string, Base64.DEFAULT);
 		} catch (Exception e) {
 		e.printStackTrace();
 		}
 		return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
 	}
 	
}
