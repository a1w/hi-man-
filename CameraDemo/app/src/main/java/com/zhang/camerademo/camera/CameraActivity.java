/*
 *
 * CameraActivity.java
 * 
 * Created by Wuwang on 2016/11/18
 * Copyright © 2016年 深圳哎吖科技. All rights reserved.
 */
package com.zhang.camerademo.camera;

import android.Manifest;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aiyaapp.camera.sdk.base.FrameCallback;
import com.aiyaapp.camera.sdk.widget.CameraView;
import com.zhang.camerademo.EffectSelectActivity;
import com.zhang.camerademo.R;
import com.zhang.camerademo.util.PermissionUtils;

import java.io.File;

/**
 * Description:
 */
public class CameraActivity extends EffectSelectActivity implements FrameCallback {

    private CameraView mCameraView;
    private int bmpWidth=720,bmpHeight=1280;

    private boolean isTakePhoto=true;
    private boolean isRecord=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtils.askPermission(this,new String[]{Manifest.permission.CAMERA, Manifest
            .permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},10,mRunnable);
    }

    private CameraView.CameraController mController=new CameraView.CameraController(){
        @Override
        protected void otherSetting(Camera.Parameters param) {
            super.otherSetting(param);
            //不支持自动聚焦，这样设置会导致崩溃
            //param.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            //设置FpsRange，应查询相机支持的fps range，然后再进行设置
            //Android 提供了这个接口，但是这个设置不一定生效，与手机有关
            //param.setPreviewFpsRange(30,30);
            bmpHeight=param.getPreviewSize().width;
            bmpWidth=param.getPreviewSize().height;
            Log.e("wuwang","bitmapSize:"+bmpWidth+"/"+bmpHeight);
            mCameraView.setFrameCallback(bmpWidth,bmpHeight,CameraActivity.this);
        }
    };

    private Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            setContentView(R.layout.activity_camera);
            mCameraView = (CameraView)findViewById(R.id.mCameraView);
            mCameraView.setCameraController(mController);
            //增加自定义滤镜
            //LookupFilter filter=new LookupFilter(getResources());
            //filter.setIntensity(0.5f);
            //filter.setMaskImage("shader/lookup/purityLookup.png");
            //mCameraView.addFilter(filter,true);
            //强制关闭人脸检测
            //AiyaEffects.getInstance().set(ISdkManager.SET_TRACK_FORCE_CLOSE,ISdkManager.TRUE);
            initData();
            mCameraView.setEffect(null);
        }
    };
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(requestCode == 10, grantResults, mRunnable, new Runnable() {
            @Override
            public void run() {
                finish();
                Toast.makeText(CameraActivity.this,"必要的权限未被允许", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mSwitchCamera:
                mCameraView.switchCamera();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if(mCameraView!=null){
            mCameraView.onResume();
        }
//        mCameraView.bringToFront();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if(mCameraView!=null){
            mCameraView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCameraView!=null){
            mCameraView.onDestroy();
        }
    }

    @Override
    public void onClick(View view){
        super.onClick(view);
        switch (view.getId()){
            case R.id.mShutter:
                if(isTakePhoto){
                    mCameraView.takePhoto();
                }else{
//                    isRecord=!isRecord;
//                    if(isRecord){
//                        mCameraView.setFrameCallback(360,648,this);
//                        mCameraView.startRecord();
//                        if(mEncoder==null){
//                            mEncoder=new CameraEncoder();
//                        }
//                        mEncoder.setSavePath(getVideoPath(System.currentTimeMillis()+".mp4"));
//                        try {
//                            mEncoder.prepare(360,640);
//                            mEncoder.start();
//                        } catch (IOException | InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }else{
//                        mCameraView.stopRecord();
//                        mEncoder.stop();
//                    }
                }
                break;
        }
    }

    //拍照或者录制的回调
    @Override
    public void onFrame(final byte[] bytes,long time) {
        if(isTakePhoto){
            saveBitmapAsync(bytes,bmpWidth,bmpHeight);
        }else{
//            mEncoder.feedData(bytes,time);
        }
    }

}
