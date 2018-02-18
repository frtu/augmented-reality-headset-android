package com.github.frtu.android.ar.bt200;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import jp.epson.moverio.bt200.AudioControl;
import jp.epson.moverio.bt200.DisplayControl;
import jp.epson.moverio.bt200.SensorControl;

/**
 * Created by fred on 18/02/2018.
 */
public class Bt200Manager {
    private Context mContext = null;

    private DisplayControl mDisplayControl = null;
    private AudioControl mAudioControl = null;
    private SensorControl mSensorControl = null;

    public Bt200Manager(Context context) {
        mContext = context;
        mDisplayControl = new DisplayControl(context);
        mAudioControl = new AudioControl(context);
        mSensorControl = new SensorControl(context);
    }

    public void bind2D3DToggleButton(ToggleButton toggleButton2D3D) {
        toggleButton2D3D.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean is3D) {
                if (is3D) {
                    mDisplayControl.setMode(DisplayControl.DISPLAY_MODE_3D, true);
                } else {
                    mDisplayControl.setMode(DisplayControl.DISPLAY_MODE_2D, false);
                }
            }
        });
    }

    public void bindMuteToggleButton(ToggleButton toggleButtonAudioMute) {
        toggleButtonAudioMute.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isMute) {
                if (isMute) {
                    mAudioControl.setMute(true);
                } else {
                    mAudioControl.setMute(false);
                }
            }
        });
    }

    /**
     * If sensor on the Controller, toggle button ON.
     * <p>
     * If sensor on the HeadSet, toggle button OFF.
     *
     * @param toggleButtonSensor
     */
    public void bindSensorToggleButton(ToggleButton toggleButtonSensor) {
        // Set initial state
        if (SensorControl.SENSOR_MODE_CONTROLLER == mSensorControl.getMode()) {
            toggleButtonSensor.setChecked(true);
        } else {
            toggleButtonSensor.setChecked(false);
        }

        // Set listener
        toggleButtonSensor.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSensorController) {
                if (isSensorController) {
                    mSensorControl.setMode(SensorControl.SENSOR_MODE_CONTROLLER);
                } else {
                    mSensorControl.setMode(SensorControl.SENSOR_MODE_HEADSET);
                }
            }
        });
    }
}
