package com.github.frtu.android.ar.bt200;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the Camera Manager for prior than Android 5 (API 21) that deprecate Camera in favor of camera2.
 * <p>
 * See : https://developer.android.com/guide/topics/media/camera.html
 * <p>
 * Created by fred on 11/03/2018.
 */
public class CameraManager {
    private static final Logger logger = LoggerFactory.getLogger(CameraManager.class);

    private Context mContext = null;

    public CameraManager(Context context) {
        mContext = context;
    }

    /**
     * Check if this device has a camera
     */
    public boolean checkCameraHardware() {
        if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            logger.debug("This device has a camera");
            return true;
        } else {
            logger.debug("No camera on this device");
            return false;
        }
    }

    /**
     * Proxy method for Camera.getNumberOfCameras()
     */
    public static int getNumberOfCameras() {
        int numberOfCameras = Camera.getNumberOfCameras();
        logger.info("Found {} camera on this device", numberOfCameras);
        return numberOfCameras;
    }

    /**
     * A safe way to get an instance of the Camera object.
     *
     * @return null if camera is unavailable or in use
     */
    public static Camera getCameraInstance(int index) {
        Camera c = null;
        int numberOfCameras = getNumberOfCameras();
        if (numberOfCameras > 0) {
            if (index >= numberOfCameras) {
                logger.warn("ATTENTION try to get a camera index={} higher than available={}. Return the latest index={} and continue.", index, numberOfCameras, numberOfCameras - 1, new IllegalArgumentException());
                index = numberOfCameras - 1;
            }
            try {
                c = Camera.open(index); // attempt to get a Camera instance
            } catch (Exception e) {
                // Camera is not available (in use or does not exist)
                logger.error(e.getMessage(), e);
            }
        } else {
            logger.error("Getting index={} is impossible, where total number of camera = {}", index, numberOfCameras);
        }
        return c; // returns null if camera is unavailable
    }
}
