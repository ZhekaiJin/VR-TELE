# VR-TELE
=========================================
https://docs.google.com/document/d/16Llf9d7UICQfcfpNQS2lqCRWslY9GL7KxqAbc5ZgvGo/edit?usp=sharing

## Description ##

Independent Project: Realization of telepresence \
Instructor: Prof. Neveen Shlayan\
Memebers: Zhekai Jin Simon Shao

## Functionality ##
* Online surveillance 
* Motion detection and 
* VR view of Pi-video stream

## Dependencies ##
* JVM
* Python
* make
* Leapmotion open-source Library 


## Compilation and Usage ##
```bash
make
[to be update]
```

## Compilation and Usage on Pi ##
* Step 1: Install Raspbian on your RPi
* Step 2: Attach camera to RPi and enable camera support (http://www.raspberrypi.org/camera)
* Step 3: Update your RPi with the following commands:
```bash
sudo apt-get update
sudo apt-get dist-upgrade
```
Occasionally if camera core software updates have been done then a sudo rpi-update may be used to benefit from these before they become available as standard.

* Step 4:

For Jessie Lite run sudo apt-get install git

Clone the code from github and enable and run the install script with the following commands:
```bash
git clone https://github.com/silvanmelchior/RPi_Cam_Web_Interface.git
cd RPi_Cam_Web_Interface
./install.sh
```
*Please refer to RPi_Cam_Web_Interface for further web dev [https://github.com/ZhekaiJin/RPi_Cam_Web_Interface]
