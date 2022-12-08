# Calculator AllWinnerA50

This is a repository stored some reverse engineering for an Android calculator using AllWinner A50.

## Install

- Force-stop the original calculator app.

- Download the APK file from Releses, then install it via adb.

- Go to 'System'-'Auto run', allow 'KeyboardService' to run in background.

![](art/autorun.webp)

## Usage

Now pull down the settings panel and add the 'Keyboard' tile, then 'KeyboardService' will be working. Click the tile to toggle its status.

![](art/tile.webp)

## Key Map

- Number Mode

  ![](art/number-mode.webp)

- DPad Mode

  ![](art/dpad-mode.webp)

- Maimemo Mode (same as DPad Mode but those following buttons added)

  ![](art/maimemo-mode.webp)

## Misc

- Hide the navigation bar

  ```shell
  $ adb shell wm overscan 0,0,0,-58
  ```

- Some applications you may want to install

  launcher3: [apk/launcher3.apk](apk/launcher3.apk)

  gboard: https://www.apkmirror.com/apk/google-inc/gboard

  wallpaper-9: https://www.apkmirror.com/apk/google-inc/google-wallpaper-picker/google-wallpaper-picker-9-5526574-release

- Unnecessary applications that can be disabled

  ```shell
  $ adb shell pm disable-user [packagename]
  com.allwinnertech.gmsintegration
  com.android.bips
  com.android.bookmarkprovider
  com.android.calendar
  com.android.companiondevicemanager
  com.android.contacts
  com.android.cts.ctsshim
  com.android.cts.priv.ctsshim
  com.android.dreams.basic
  com.android.email
  com.android.gallery3d
  com.android.inputmethod.latin # use gboard
  com.android.location.fused
  com.android.mms.service
  com.android.music
  com.android.musicfx
  com.android.onetimeinitializer
  com.android.printservice.recommendation
  com.android.printspooler
  com.android.providers.blockednumber
  com.android.providers.calendar
  com.android.providers.contacts
  com.android.provision
  com.android.quicksearchbox
  com.android.server.telecom
  com.android.simappdialog
  com.android.soundrecorder
  com.softwinner.dragonfire
  com.softwinner.fireplayer
  com.softwinner.update
  com.sohu.inputmethod.sogou # use gboard
  org.chromium.webview_shell
  ```

- Unlock the OEM locking

  ```shell
  $ adb reboot bootloader
  $ fastboot oem unlock
  ```

- Mount the root partition as read-write

  ```shell
  $ adb shell
  $ su
  $ mount -o remount -o rw /
  ```
