package com.zyou.flashlight.ui.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.zyou.flashlight.R;
import com.zyou.flashlight.app.Constants;

/**
 * Created by fangzhengyou on 16/1/10.
 * 打赏页面
 */
public class RewardActivity extends BaseActivity implements View.OnLongClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        setTitle(R.string.reward);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
    }

    private void findViews() {
        ImageView ivQRCode=(ImageView)findViewById(R.id.iv_qr_code);
        ivQRCode.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {
        int id=v.getId();
        if(id==R.id.iv_qr_code){
            //保存图片到图片文件夹
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.get_mony);
            MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "qr_code_pic", "qr_code_pic");
            showOpenWxDialog();
        }
        return false;
    }

    /**
     * 显示对话框：是否打开微信APP
     */
    private void showOpenWxDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.tis).setMessage(R.string.open_wx_tis)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openWxAPP();
                        //因为微信扫一扫要用到相机，所以这里要发广播释放相机
                        sendBroadcast(new Intent(Constants.ACTION_GO_PAY));
                    }
                });
        alertDialog.create().show();
    }

    /**
     * 打开微信APP
     */
    private void openWxAPP(){
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        startActivityForResult(intent, 0);
    }

}
