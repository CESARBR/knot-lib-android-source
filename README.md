
## KNOT Android library

1. This lib is a part of KNOT solution (for more information: https://github.com/CESARBR) and it aims to provide an abstraction for Android applications to create IoT (Internet of Things) solutions using KNOT platform by HTTP and Socket protocols.

## knot-lib-android-source Goals
1. Provide abstraction to connect with KNOT cloud using HTTP or Sockets. It is possible to connect with a specific cloud, perform CRUD operations in devices, send messages, and more.
   
 * For more information about KNOT solution see: http://knot.cesar.org.br/

## Using Android library directly
1. In module app file include instruction
>
```
   dependencies {
       ...
       compile 'com.github.cesarbr:knot-lib-android-sourc:KNOT-v01.00'
       ...
   }
```
>

                                    ##OR


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