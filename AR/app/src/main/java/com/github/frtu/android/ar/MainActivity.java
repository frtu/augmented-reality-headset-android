package com.github.frtu.android.ar;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import com.github.frtu.android.ar.bt200.Bt200Manager;
import com.github.frtu.android.ar.bt200.CameraManager;
import com.github.frtu.android.ar.bt200.CameraPreviewBase;

public class MainActivity extends Activity {
    private Bt200Manager bt200Manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CameraManager cameraManager = new CameraManager(this);
        cameraManager.checkCameraHardware();
        // BT-200 CameraManager: CAMERA PREVIEW height=480 width=640 max zoom=10
        Camera camera = cameraManager.getCameraInstance(0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraPreviewBase cameraPreviewBase = new CameraPreviewBase(this, camera);
        // Create our Preview view and set it as the content of our activity.
        FrameLayout preview = (FrameLayout) findViewById(R.id.container);
        preview.addView(cameraPreviewBase);

        bt200Manager = new Bt200Manager(this);
        bt200Manager.bind2D3DToggleButton((ToggleButton) findViewById(R.id.toggleButton_2d3d));
        bt200Manager.bindSensorToggleButton((ToggleButton) findViewById(R.id.toggleButton_sensor));
        bt200Manager.bindMuteToggleButton((ToggleButton) findViewById(R.id.toggleButton_amute));
    }
}
