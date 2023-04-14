package com.vistony.salesforce.Controller.Utilitario;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class VideoRecorder{
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private boolean isRecording = false;

    public boolean startRecording(SurfaceHolder holder, String filePath) {
        try {

        if (isRecording) {
            return false;
        }

        mCamera = Camera.open();

        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));
        mMediaRecorder.setOutputFile(filePath);
        mMediaRecorder.setPreviewDisplay(holder.getSurface());
        mMediaRecorder.setMaxFileSize(5000000);

            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isRecording = true;
            return true;
        } catch (IOException e) {
            releaseMediaRecorder();
            releaseCamera();
            Log.e("REOS","VideoRecorder-startRecording-error"+e.toString());
            return false;
        }
    }

    public boolean stopRecording() {
        if (!isRecording) {
            return false;
        }

        try {
            mMediaRecorder.stop();
            releaseMediaRecorder();
            releaseCamera();
            isRecording = false;
            return true;
        } catch (Exception e) {
            releaseMediaRecorder();
            releaseCamera();
            return false;
        }
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mCamera.lock();
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

}
