package com.example.sli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    JavaCameraView javaCameraView;

    Mat mat1, mat2;
    Scalar scalarLow, scalarHigh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OpenCVLoader.initDebug();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //to remove the action bar (title bar)
        getSupportActionBar().hide();

        javaCameraView = (JavaCameraView) findViewById(R.id.cameraview);
        javaCameraView.setCameraIndex(1);
        scalarLow = new Scalar(0, 58, 30);
        scalarHigh = new Scalar(33, 255, 255);
        javaCameraView.setCvCameraViewListener(this);
        javaCameraView.enableView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        javaCameraView.disableView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        javaCameraView.enableView();
    }

    @Override
    protected void onDestroy() {
        javaCameraView.disableView();
        super.onDestroy();

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mat1 = new Mat(width,height,CvType.CV_16UC4);
        mat2 = new Mat(width,height,CvType.CV_16UC4);


    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Imgproc.cvtColor(inputFrame.rgba(),mat1,Imgproc.COLOR_RGB2HSV);
        Core.inRange(mat1,scalarLow,scalarHigh,mat2);
        Core.flip(mat2,mat2,1);

        return mat2;

    }

}

