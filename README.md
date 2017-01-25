## KNOT Android library

1. This lib is a part of KNOT solution (for more information: https://github.com/CESARBR) and has aims to provide abstractions for Android application create owner IoT (Internet of Things) solution in KNOT platform by HTTP protocol and via SocketIO.

## knot-lib-android-source Goals
1. Provide abstraction to connect with KNOT cloud by HTTP or SocketIO, it is possible to cofigure with specific cloud, CRUD of devices, send messages and others.
   
 * For more information about KNOT solution see: http://knot.cesar.org.br/

## Generated .aar file of knot_lib

1. In root directory project run command: **./gradlew clean aR** to generated this file.
   file is generated in a folder: root_project/androidlibrary/build/outputs/aar/

## Using Android library

1. Add file created with command above in libs folder.

   *Important: if not seen libs folder, change AndroidStudio perspective to Project.
      
2. Change allprojects attribute in project gradle to:
>
```
    allprojects {
        repositories {
              jcenter()
              flatDir {
                 dirs 'libs'
              }
          }
    }
```
>

3. In Module: app add the following command:
>
```
  compile(name:'knot-android-library-release', ext:'aar')
```
>
