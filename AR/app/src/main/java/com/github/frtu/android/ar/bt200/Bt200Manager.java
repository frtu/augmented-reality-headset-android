package com.github.frtu.android.ar.bt200;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.github.frtu.android.ar.arrakis.ArrakisManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.epson.moverio.bt200.AudioControl;
import jp.epson.moverio.bt200.DisplayControl;
import jp.epson.moverio.bt200.SensorControl;

/**
 * Created by fred on 18/02/2018.
 */
public class Bt200Manager {
    private static final Logger logger = LoggerFactory.getLogger(ArrakisManager.class);

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
                    logger.info("set 3D display mode.");
                    mDisplayControl.setMode(DisplayControl.DISPLAY_MODE_3D, true);
                } else {
                    logger.info("set 2D display mode.");
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
                    logger.info("Set audio mute ON.");
                    mAudioControl.setMute(true);
                } else {
                    logger.info("set audio mute OFF.");
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
                    logger.info("set sensor of controller.");
                    mSensorControl.setMode(SensorControl.SENSOR_MODE_CONTROLLER);
                } else {
                    logger.info("set sensor of headset.");
                    mSensorControl.setMode(SensorControl.SENSOR_MODE_HEADSET);
                }
            }
        });
    }
}
