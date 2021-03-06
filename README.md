# Augmented Reality headset using android
Project to create an AR (Augmented Reality) project to deploy into a binocular glasses headset using Android.


## Moverio BT-200

### Tech specificiation

[BT-200](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_tiw1405ce.pdf) :

* System Software (Android 4.0.4)
* Android (API 15)
* Resolution 2D (960x540) / 3D (480x540 each eye) 
* Screen Density mdpi (160dpi)
* USB Vendor ID (0x04B8)
* FOV - Field Of View (20 degree)

### Setting project & startup

Getting started :

* [Read the software license and Download at BT200Ctrl.jar](https://tech.moverio.epson.com/en/bt-200/sdk_download.html)
* Unzip the file and copy BT200Ctrl.jar into AR/app/libs/*.jar

Then you can use Bt200Manager class to bind ToggleButton to BT-200 features :

```Java
//In any Activity class
Bt200Manager bt200Manager = new Bt200Manager(this);

bt200Manager.bind2D3DToggleButton((ToggleButton) findViewById(R.id.toggleButton_2d3d));
bt200Manager.bindSensorToggleButton((ToggleButton) findViewById(R.id.toggleButton_sensor));
bt200Manager.bindMuteToggleButton((ToggleButton) findViewById(R.id.toggleButton_amute));
```

When starting up look for this in LogCat :

```
.. I/Bt200Manager: Detected EPSON BT-200 deviceName=EPSON embt2
```

If the device model is not the one expected, deactivate instead of failing triggering features :

```
.. I/Bt200Manager: NOT EPSON BT-200, deactivate features ! deviceName=Xxx
```

### Setting up dev env

#### Setup Moverio BT-200 <-> adb & Android Studio

[HARDWARE UPDATE - Setting up BT-200 into Developper mode](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_dos1602_en.pdf)

Connect with Android Studio :

* [on Windows](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_adb1505a_en.pdf)
* on Mac directly [jump to Sheet 8](https://tech.moverio.epson.com/en/bt-200/pdf/bt200_adb1505a_en.pdf)

#### OpenCV

##### Importing the project

[Download OpenCV for Android SDK](https://github.com/opencv/opencv/releases)

To avoid very long build without response, it is recommendated to edit the file unpacked_OpenCV_package/sdk/java/build.gradle before importing Module.

```
    compileSdkVersion commonCompileSdkVersion
    buildToolsVersion commonBuildToolsVersion

    defaultConfig {
        minSdkVersion commonMinSdkVersion
        targetSdkVersion commonTargetSdkVersion
    }
```

Copy the folder ARCH/libopencv_java3.so corresponding to your mobile architecture into your application src/main/jniLibs/

##### Preparing the target device

* Install the OpenCV Manager APK corresponding to your env (in subfolder /apk/*). Ex ARM processor :
```
adb install -r apk/OpenCV_3.4.1_Manager_3.41_armeabi-v7a.apk
```

* If you have pre-existing OpenCV Mgr version uninstall with :
```
adb uninstall -v org.opencv.engine
```

##### OpenCV full guidelines

* [Follow the tutorial guide here](https://docs.opencv.org/3.4.0/d0/d6c/tutorial_dnn_android.html)
* [Simple guideline with Emulator](https://zami0xzami.wordpress.com/2016/03/17/opencv-for-mobile-devices-using-android-studio/)

#### Vuforia & Unity

* [Download unity-3.5.1 for Mac](http://download.unity3d.com/download_unity/unity-3.5.1.dmg)
* [Release notes - Vuforia version and BT-200 drop after 6.5](https://library.vuforia.com/articles/Release_Notes/Vuforia-SDK-Release-Notes)


#### More documentation

* [Getting started & Presentation](https://www.slideshare.net/prelaunchlabs/epson-moverio-bt200-developer-getting-started)
* [BT-200 Developer Network](https://tech.moverio.epson.com/en/bt-200/)
* [All Moverio BT-200 documentation](https://tech.moverio.epson.com/en/bt-200/tools.html)

## Advices for developping on Android

### Library version

When getting / copying sources from other repo, just make sure **compileSdkVersion** must match the support library.

Android Studio : 

* Go to **Project Structure > app > Dependencies**
* Remove any lib
* Replace by adding + maven "Library Dependency"


Manually 

* in build.gradle (Module: app) 
* make sure to add the correct lib version :

```JSON
android {
    compileSdkVersion 27
    ...
}
...
dependencies {
    compile 'com.android.support:appcompat-v7:27.0.2'
    compile 'com.android.support:support-v4:27.0.2'
}
```

[Check latest Android lib support](https://developer.android.com/topic/libraries/support-library/packages.html)

### Android Basics

#### Version check

[Android version by Code names](https://developer.android.com/reference/android/os/Build.VERSION_CODES)

#### Permissions

Starting from Android 6.0 (API level 23), Permission is defined by Apps. To check & request permission :
[Android Permission](https://developer.android.com/training/permissions/requesting#java)

### Support Libraries

* [Officiel Android support lib description](https://developer.android.com/topic/libraries/support-library/index.html)
* [Stackoverflow - good feature listing of v4, v7, v13 support](https://stackoverflow.com/questions/29049908/appcompat-compatibility-and-support-libraries-for-lollipop-if-minimum-sdk-14)

##### Material Design

* [Material Design backport for older than 5.0 version](https://developer.android.com/training/material/compatibility.html)

##### Device name

* [AndroidDeviceNames in GitHub](https://github.com/shubhamsharmacs/AndroidDeviceNames)

### Adding C/C++ source code
#### NDK Environment setup

* [Android studio - Install NDK, CMake & LLDB](https://developer.android.com/studio/projects/add-native-code.html#download-ndk)
* [Android studio - Link gradle to your make file Android.mk](
https://developer.android.com/studio/projects/gradle-external-native-builds.html#link-with-ui)
* [Guide for Android.mk](https://developer.android.com/ndk/guides/android_mk.html)

#### Coding

* [Create your C/C++ source & header files](https://developer.android.com/studio/projects/add-native-code.html#create-sources)

