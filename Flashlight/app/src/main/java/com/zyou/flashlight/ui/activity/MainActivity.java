package com.zyou.flashlight.ui.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.umeng.update.UmengUpdateAgent;
import com.zyou.flashlight.R;

public class MainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


    private Camera mCamera;
    private Camera.Parameters parameters;
    public static final int OPEN_LIGHT = 0x0010;
    public static final int CLOSE_LIGHT = 0x0020;
    private FlightThread flightThread;

    private PowerManager.WakeLock mWakeLock;

    private UIHandler uiHandler;
    private boolean isStart = false; //闪光灯线程判断变量
    private int i = 0;

    private ToggleButton tbLight;
    private ToggleButton tbFlashlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);

        findViews();
        initViews();
    }

    private void findViews() {
        tbLight = (ToggleButton) findViewById(R.id.btnLight);
        tbFlashlight = (ToggleButton) findViewById(R.id.btnFlashLight);

        tbLight.setOnCheckedChangeListener(this);
        tbFlashlight.setOnCheckedChangeListener(this);
    }

    private void initViews() {
        flightThread = new FlightThread();
        uiHandler=new UIHandler();
        flightThread.start();

        UmengUpdateAgent.update(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
        parameters = mCamera.getParameters();

        //保持屏幕常亮
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tbLight.setChecked(false);
        tbFlashlight.setChecked(false);
        turnOnOffLight(false);
        isStart=false;
        mCamera.release();//释放相机

        //保持屏幕常亮
        mWakeLock.release();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == R.id.btnLight) {
            turnOnOffLight(isChecked);
            return;
        }
        if (id == R.id.btnFlashLight) {
            if (isChecked) {
                isStart = true;
            } else {
                isStart = false;
                turnOnOffLight(false);
            }
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_item_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.to_you));
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
            startActivity(intent);
        }
        if(id==R.id.menu_item_reward){
            startActivity(new Intent(this, RewardActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    class FlightThread extends Thread {
        @Override
        public void run() {
            while (true) {
                while (isStart) {
                    if (i++ % 2 == 0) { //取余运算，如果为0则开灯，如果不为0则关灯，以此达到闪烁效果
                        uiHandler.obtainMessage(OPEN_LIGHT).sendToTarget();
                    } else {
                        uiHandler.obtainMessage(CLOSE_LIGHT).sendToTarget();
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OPEN_LIGHT:
                    turnOnOffLight(true);
                    break;
                case CLOSE_LIGHT:
                    turnOnOffLight(false);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 打开或关闭手电
     */
    private void turnOnOffLight(boolean onOrOff) {
        if (onOrOff){
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        }else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            i=0;
        }
    }

    @Override
    protected void onDestroy() {

        flightThread.interrupt();//关闭线程
        super.onDestroy();
    }

}
