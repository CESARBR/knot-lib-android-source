## Using Android library

1. Add maven repo to gradle:
>
```
   allprojects {
        repositories {
            maven {
                url "https://gitlab.cesar.org.br/cesar-iot/knot-android-lib-source/raw/mvn-repo"
            }
        }
    }
```
>

2. Add the dependency to app gradle:
>
```
    compile 'br.org.cesar:knot-library:1.0.0'
```
>
