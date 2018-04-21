package com.github.frtu.android.ar;

import android.app.Activity;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.github.frtu.android.ar.bt200.Bt200Manager;
import com.github.frtu.android.ar.camera.CameraManager;
import com.github.frtu.android.ar.camera.CameraPreviewBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class MainActivity extends Activity {
    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private Bt200Manager bt200Manager;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CameraManager cameraManager = new CameraManager(this);
        cameraManager.hasCameraHardware();
        // BT-200 CameraManager: CAMERA PREVIEW height=480 width=640 max zoom=10
        mCamera = cameraManager.getCameraInstance(0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraPreviewBase cameraPreviewBase = new CameraPreviewBase(this, mCamera);
        // Create our Preview view and set it as the content of our activity.
        FrameLayout preview = findViewById(R.id.container);
        preview.addView(cameraPreviewBase);


        // Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logger.debug("get an image from the camera");
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );

        bt200Manager = new Bt200Manager(this);
        bt200Manager.bind2D3DToggleButton((ToggleButton) findViewById(R.id.toggleButton_2d3d));
        bt200Manager.bindSensorToggleButton((ToggleButton) findViewById(R.id.toggleButton_sensor));
        bt200Manager.bindMuteToggleButton((ToggleButton) findViewById(R.id.toggleButton_amute));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCamera = CameraManager.releaseCamera(mCamera);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCamera = CameraManager.releaseCamera(mCamera);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera = CameraManager.releaseCamera(mCamera);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                logger.error("Error creating media file, check storage permissions: {}");
                return;
            }

            try {
                logger.debug("Writing file at {}", pictureFile.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                logger.error("File not found: {}", e.getMessage(), e);
            } catch (IOException e) {
                logger.error("Error accessing file: {}", e.getMessage(), e);
            }
        }
    };


    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                logger.error("failed to create directory: {}", mediaStorageDir.getAbsolutePath());
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
