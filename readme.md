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
  > adb shell wm overscan 0,0,0,-58
  ```

- Some applications you may want to install

  Launcher3: [apk/launcher3.apk](apk/launcher3.apk)

  Gboard: https://www.apkmirror.com/apk/google-inc/gboard

  Wallpaper-9: https://www.apkmirror.com/apk/google-inc/google-wallpaper-picker/google-wallpaper-picker-9-5526574-release

  Coolapk-v7: https://t.me/fuck_coolapk/119

  Netease Music: https://t.me/fuck_coolapk_chat/43149

- Unnecessary applications that can be disabled

  ```shell
  > adb shell pm disable-user [packagename]
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
  > adb reboot bootloader
  > fastboot oem unlock
  ```

- Mount the root partition as read-write

  ```shell
  > adb shell
  > su
  > mount -o remount -o rw /
  ```

- Dump raw images

  **It is highly recommended to do this to backup your device.** Since Adbd may return unauthenticated on Linux on my side, use macOS instead.

  ```shell
  > adb reboot recovery
  > adb root
    timeout expired while waiting for device
  > adb ls /dev/block/by-name
    000041ed 00000140 63902b01 .
    000041ed 000003c0 63902a1a ..
    0000a1ff 00000015 63902a1a media_data
    0000a1ff 00000015 63902a1a dtbo
    0000alff 00000015 63902a1a empty
    0000a1ff 00000015 63902a1a frp
    0000a1ff 00000015 63902ala private
    0000a1ff 00000015 63902a1a metadata
    0000a1ff 00000014 63902a1a cache
    0000a1ff 00000014 63902a1a recovery
    0000a1ff 00000014 63902a1a misc
    0000a1ff 00000014 63902a1a vendor
    0000a1ff 00000014 63902a1a boot
    0000a1ff 00000014 63902a1a env
    0000a1ff 00000014 63902a1a bootloader
    0000a1ff 00000014 63902a1a UDISK
  > adb pull /dev/block/by-name/[partition] [filename]
  > img2simg [filename] [filename] # convert raw img to android sparse img that can be flash in fastboot mode
  ```

- TWRP/Fastboot images

  [Download from here](https://drive.lz233.ac.cn/%E5%B7%A5%E7%A8%8B/%E5%88%B7%E6%9C%BA%E7%9B%B8%E5%85%B3/allwinnera50/fastboot), or see 'Dump raw images'.

  Touch screen doesn't work in TWRP now, use OTG devices. Fastboot driver may be missing on Windows and macOS, finally I tested Fedora 37 and it works.

  ```shell
  > adb reboot bootloader
  > fastboot flash recovery twrp.img
  > fastboot reboot
  > adb reboot recovery
  ```
  
  ![](art/twrp.webp)

- Allwinner FEL mode

  Power off the device, press on the 'UPDATE' button, then connect to PC and click the 'POWER' button 5-10 times, your device will go to FEL mode. Use [Sunxi-tools](https://github.com/linux-sunxi/sunxi-tools) to perform further steps.

  ![](art/board.webp)