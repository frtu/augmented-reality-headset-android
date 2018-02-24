package com.github.frtu.android.ar.bt200;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.epson.moverio.bt200.AudioControl;
import jp.epson.moverio.bt200.DisplayControl;
import jp.epson.moverio.bt200.SensorControl;

/**
 * Wrap all BT-200 specificy with direct setting mode.
 *
 * Allow to bind methods behind {@link ToggleButton}.
 *
 * Created by fred on 18/02/2018.
 */
public class Bt200Manager {
    private static final Logger logger = LoggerFactory.getLogger(Bt200Manager.class);

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

    public void setMode2D() {
        logger.info("set 2D display mode.");
        mDisplayControl.setMode(DisplayControl.DISPLAY_MODE_2D, false);
    }

    public void setMode3D() {
        logger.info("set 3D display mode.");
        mDisplayControl.setMode(DisplayControl.DISPLAY_MODE_3D, true);
    }

    public void setAudioMuteOff() {
        logger.info("set audio mute OFF.");
        mAudioControl.setMute(false);
    }

    public void setAudioMuteOn() {
        logger.info("Set audio mute ON.");
        mAudioControl.setMute(true);
    }

    public void setSensorOnHeadSet() {
        logger.info("set sensor of headset.");
        mSensorControl.setMode(SensorControl.SENSOR_MODE_HEADSET);
    }

    public void setSensorOnController() {
        logger.info("set sensor of controller.");
        mSensorControl.setMode(SensorControl.SENSOR_MODE_CONTROLLER);
    }

    public boolean isSensorOnController() {
        return SensorControl.SENSOR_MODE_CONTROLLER == mSensorControl.getMode();
    }

    public void bind2D3DToggleButton(ToggleButton toggleButton2D3D) {
        toggleButton2D3D.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean is3D) {
                if (is3D) {
                    setMode3D();
                } else {
                    setMode2D();
                }
            }
        });
    }

    public void bindMuteToggleButton(ToggleButton toggleButtonAudioMute) {
        toggleButtonAudioMute.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isMute) {
                if (isMute) {
                    setAudioMuteOn();
                } else {
                    setAudioMuteOff();
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
        if (isSensorOnController()) {
            toggleButtonSensor.setChecked(true);
        } else {
            toggleButtonSensor.setChecked(false);
        }

        // Set listener
        toggleButtonSensor.setOnCheckedChangeListener(new ToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSensorController) {
                if (isSensorController) {
                    setSensorOnController();
                } else {
                    setSensorOnHeadSet();
                }
            }
        });
    }
}
