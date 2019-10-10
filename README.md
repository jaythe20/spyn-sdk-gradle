# Spyn Android SDK

Spyn Android SDK allows you to offer Spyn Launcher in your own app. SpynSDK is our Android module which will allow your Android app to communicate with Spyn.

# Help
Integration questions can be answered by email (contact@spare.io) or by slack ch

# Installation

- Import SpynSDK module
Open project level gradle and make following changes
```appgradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
 Open app level gradle and make following changes
```projectlevel
dependencies {
		implementation 'com.github.User:Repo:0.0.5'
	}
```
# Initialisation
- Create Application class or Open if you have already. 
You can find more details to create application class from here https://github.com/codepath/android_guides/wiki/Understanding-the-Android-Application-Class
Make Following changes :
```
public class MyApplication extends Application {
 public static String dealId = "place your deal id here";
 @Override
    public void onCreate() {
        super.onCreate();
          Spyn.initSpyn(null, dealId, "en", this);
    }
}
```
You can start using it now. Whenver necessary get instance of spyne and call the functions.
```java
Spyn.getInstance();
```

## Offering Spyn

Offering Spyn is the process in which the user will be prompted to Install Spyn Launcher in order to unlock a specific reward. When and where you decide to offer Spyn is up to you, and can be fully customizable. You can launch the Spyn offer flow using the `offerSpyn` function:

```java
Spyn.getInstance().offerSpyn();
```
