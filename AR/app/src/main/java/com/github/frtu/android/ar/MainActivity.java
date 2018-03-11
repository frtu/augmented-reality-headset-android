package com.github.frtu.android.ar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ToggleButton;

import com.github.frtu.android.ar.bt200.Bt200Manager;
import com.github.frtu.android.ar.bt200.CameraManager;

public class MainActivity extends Activity {
    private Bt200Manager bt200Manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CameraManager cameraManager = new CameraManager(this);
        cameraManager.checkCameraHardware();
        cameraManager.getCameraInstance(0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt200Manager = new Bt200Manager(this);
        bt200Manager.bind2D3DToggleButton((ToggleButton) findViewById(R.id.toggleButton_2d3d));
        bt200Manager.bindSensorToggleButton((ToggleButton) findViewById(R.id.toggleButton_sensor));
        bt200Manager.bindMuteToggleButton((ToggleButton) findViewById(R.id.toggleButton_amute));
    }
}
