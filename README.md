# Augmented Reality headset using android
Project to create an AR (Augmented Reality) project to deploy into a binocular glasses headset using Android.

## Moverio BT-200

#### Tech specificiation

[BT-200](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_tiw1405ce.pdf) :

* System Software (Android 4.0.4)
* Android (API 15)
* Resolution 2D (960x540) / 3D (480x540 each eye) 
* Screen Density mdpi (160dpi)
* USB Vendor ID (0x04B8)
* FOV - Field Of View (20 degree)

#### Software project

Getting started :

* [Read the software license and Download at BT200Ctrl.jar](https://tech.moverio.epson.com/en/bt-200/sdk_download.html)
* Unzip the file and copy BT200Ctrl.jar into AR/app/libs/*.jar

Then you can use Bt200Manager class to bind ToggleButton to BT-200 features :

```Java
//In any Activity class
Bt200Manager bt200Manager = new Bt200Manager(this);

bt200Manager.bind2D3DToggleButton((ToggleButton) findViewById(R.id.toggleButton_2d3d));
bt200Manager.bindSensorToggleButton((ToggleButton) findViewById(R.id.toggleButton_sensor));
bt200Manager.bind2D3DToggleButton((ToggleButton) findViewById(R.id.toggleButton_amute));
```

#### Setup Moverio BT-200 <-> adb & Android Studio

[HARDWARE UPDATE - Setting up BT-200 into Developper mode](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_dos1602_en.pdf)

Connect with Android Studio :

* [on Windows](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_adb1505a_en.pdf)
* on Mac directly [jump to Sheet 8](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_adb1505a_en.pdf)


#### More documentation

[All Moverio BT-200 documentation](https://tech.moverio.epson.com/en/bt-200/tools.html)
