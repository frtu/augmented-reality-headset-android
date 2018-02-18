package com.github.frtu.android.ar.bt200;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import jp.epson.moverio.bt200.DisplayControl;

/**
 * Created by fred on 18/02/2018.
 */
public class Bt200Manager {
    private Context mContext = null;

    private DisplayControl mDisplayControl = null;

    public Bt200Manager(Context context) {
        mContext = context;
        mDisplayControl = new DisplayControl(context);
    }

    public void set2D3DToggleButton(ToggleButton toggleButton2D3D) {
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
}
