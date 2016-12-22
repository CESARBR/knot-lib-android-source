## Generated .aar file of knot_lib

1. In root directory project run command: ./gradlew clean aR to generated this file.

## Using Android library

1. Add file created with command above in libs folder.

   *Important: if not see libs folder change perspective to Project in android studio.*
      
2. Change allprojects attribute in project gradle to:
    
    allprojects {
        repositories {
              jcenter()
              flatDir {
                 dirs 'libs'
              }
          }
    }
    
3. In Module: app add the following command:
  
  compile(name:'knot-android-library-release', ext:'aar') 

