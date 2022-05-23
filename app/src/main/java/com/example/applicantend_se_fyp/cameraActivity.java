package com.example.applicantend_se_fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.security.auth.callback.Callback;

public class cameraActivity extends AppCompatActivity {
    private Camera camera;
    private MyView myView;
    private View view2, view3, view4;
    private TextView view1;
    private Button takepicture_button;
    private SurfaceView surfaceview;
    private int screenWidth, screenHeight;
    private int myViewPaddingLeft = 1200, myViewPaddingTop = 700;
    private ToneGenerator tone;


    private Camera.AutoFocusCallback myAutoFocusCallback = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            Log.w("print", "聚焦完成，，，，");            //聚焦后发出提示音
            tone = new ToneGenerator(AudioManager.STREAM_MUSIC,ToneGenerator.MAX_VOLUME);
            tone.startTone(ToneGenerator.TONE_PROP_BEEP2);
        }
    };
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//把屏幕设置成横屏
        setContentView(R.layout.activity_camera);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        view1 = (TextView) findViewById(R.id.view1);
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) view1.getLayoutParams();
        layoutParams1.height = myViewPaddingTop / 2;
        view1.setLayoutParams(layoutParams1);
        myView = (MyView) findViewById(R.id.myView);
        myView.setMyParams(screenWidth, screenHeight, myViewPaddingLeft, myViewPaddingTop);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) myView.getLayoutParams();
        layoutParams.width = screenWidth - myViewPaddingLeft;
        layoutParams.height = screenHeight - myViewPaddingTop;
        myView.setLayoutParams(layoutParams);
        view2 = findViewById(R.id.view2);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view2.getLayoutParams();
        layoutParams2.width = myViewPaddingLeft / 2;
        layoutParams2.height = screenHeight - myViewPaddingTop;
        view2.setLayoutParams(layoutParams2);
        view3 = findViewById(R.id.view3);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view3.getLayoutParams();
        layoutParams3.width = myViewPaddingLeft / 2;
        layoutParams3.height = screenHeight - myViewPaddingTop;
        view3.setLayoutParams(layoutParams3);
        view4 = findViewById(R.id.view4);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) view4.getLayoutParams();
        layoutParams4.height = myViewPaddingTop / 2;
        view4.setLayoutParams(layoutParams4);
        surfaceview = (SurfaceView) findViewById(R.id.surfaceView);
        myView.setOnTouchListener((v, event) -> {
            camera.autoFocus(myAutoFocusCallback);
            return false;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        SurfaceHolder holder = surfaceview.getHolder();
        holder.setKeepScreenOn(true);
        holder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        holder.addCallback(new MySurfaceCallback());
        holder.lockCanvas();



        takepicture_button = findViewById(R.id.takepicture_button);
        takepicture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                takepicture(view2);
            }
        });
    }
    private final class MySurfaceCallback implements Callback, SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {

        }
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                Log.d("test","created");
                camera = Camera.open();
                Camera.Parameters params = camera.getParameters();
                camera.setPreviewDisplay(surfaceview.getHolder());
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Build.VERSION.SDK_INT >= 30){
                if (!Environment.isExternalStorageManager()){
                    Intent getpermission = new Intent();
                    getpermission.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(getpermission);
                }
            }
        }
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.release();
                camera = null;
            }
        }
    }
    public void takepicture(View v) {
        Log.d("test","take pic");
        camera.takePicture(mShutterCallback, null,mPictureCallback);
    }
    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback(){
        public void onShutter() {

        }
    };

    private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap mBitmapCut = ImageCrop(mBitmap);
            String rootPath = Environment.getExternalStorageDirectory().toString() + File.separator;
            String photoPath = rootPath + System.currentTimeMillis() + ".jpg";
            File file = new File(photoPath);
            try {
                file.createNewFile();
                BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                mBitmapCut.compress(Bitmap.CompressFormat.JPEG, 100, os);
                os.flush();
                os.close();
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();

                // newView 2.1.2
                Intent intent = new Intent(cameraActivity.this, GetAPIActivity.class);
                intent.putExtra("photoUri",Uri.fromFile(new File(photoPath)).toString());

                // send camera data
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    public Bitmap ImageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int x = (int) ((w * (myViewPaddingLeft / 2)) / screenWidth);
        int y = (int) (h * (myViewPaddingTop / 2) / screenHeight);
        int retX = (int) ((w * (screenWidth - myViewPaddingLeft)) / screenWidth);
        int retY = (int) (h * (screenHeight - myViewPaddingTop) / screenHeight);
        return Bitmap.createBitmap(bitmap, x, y, retX, retY, null, false);    }






}