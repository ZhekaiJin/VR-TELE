# Configuration guide 
***Before Proceeding : Make sure you have leap Motion installed and able to run.*** [link](https://www.leapmotion.com/setup/desktop/)
## Discritption : 
   This is a toturial on how to install the publisher side on VR_TELE. 
## Dependency : 
    * JVM
    * JDK 1.6 - 1.8
    * PubNub - Data Streaming Service. [Free Sandbox]
    * Leap Motion Visualizer and Java SDK
    * Java IDE - Eclipse, JGrasp, NetBeans, IntelliJ, or Eclipse
    
Package Name | Version
--- | --- 
*slf4j-simple* | `1.6.1` 
*slf4j-api* | `1.6.0`
*pubnub* | `3.7.4`
*pubnub-gson* | `4.16.0-all` 
*LeapJava*| `N/A`
*json* | `20140107`
*LeapMotion SDK* | [Link](https://developer.leapmotion.com/get-started/)

> [Setting up a project](https://developer.leapmotion.com/documentation/java/devguide/Project_Setup.html)

Notice that using command line compilation exactly as the site said will lead to errors since not only leapmotion JDK is invovled in the class path.
I would recommend to build it with a JAVA IDE where the environment automatically track the packages location for you.
![Imgur](https://i.imgur.com/PmrXBIt.jpg)

check the [Eclipse Section](https://developer.leapmotion.com/documentation/java/devguide/Project_Setup.html).

All the packages you need is shared [here](https://www.dropbox.com/sh/798j9k3xnw0u0se/AAAlWdkjiKRtnIhMVT1cFY8Za?dl=0)
 and please download the SDK in the table link provided.

## Demo on my Eclipse IDE on my machine: 
```shell
distribution[Darwin blablall.local 17.2.0 Darwin Kernel Version 17.2.0: Fri Sep 29 18:27:05 PDT 2017; 
root:xnu-4570.20.62~3/RELEASE_X86_64 x86_64]
```
![Imgur](https://i.imgur.com/ojWwn8X.jpg)






