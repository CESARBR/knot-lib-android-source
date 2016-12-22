## Using Android library

1. Add maven repo to gradle:
>
```
   allprojects {
        repositories {
            maven { 
               url 'https://jitpack.io' 
            }
        }
    }
```
>

2. Add the dependency to app gradle:
>
```
    compile 'com.github.knot-noow:knot-lib-android-source:1.0.0'
```
>
