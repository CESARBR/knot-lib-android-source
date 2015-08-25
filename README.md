# MeshbluKit-Android

## Installation

- In library `build.gradle` add the dependency:

```gradle
dependencies {
    compile 'com.octoblu:meshblukit:1.0.0'
    compile 'com.octoblu:sanejsonobject:4.0.1'
}
```

- jCenter will need to be in the repository list in the root project `build.gradle`

```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
    }
}
```

## App Examples

* [BeaconBlu](https://github.com/octoblu/BeaconBlu-Android)
* [Gateblu](https://github.com/octoblu/gateblu-android)

## Import

```java
import com.octoblu.meshblukit.Meshblu;
import com.octoblu.sanejsonobject.SaneJSONObject;
```

## API

### Class `Meshblu`:

`import com.octoblu.meshblukit.Meshblu`

- **Constructor:**

```java
SaneJSONObject meshbluConfig = new SaneJSONObject();
meshbluConfig.putOrIgnore("uuid", uuid);
meshbluConfig.putOrIgnore("token", token);
meshbluConfig.putOrIgnore("server", "meshblu.octoblu.com");
meshbluConfig.putIntOrIgnore("port", 443);
new Meshblu(meshbluConfig, this); // 'this' is the application context
```

- **Set Credentials:**

Update the Credentials for Meshblu.

```java
SaneJSONObject meshbluConfig = new SaneJSONObject();
meshbluConfig.putOrIgnore("uuid", uuid);
meshbluConfig.putOrIgnore("token", token);
meshbluConfig.putOrIgnore("server", "meshblu.octoblu.com");
meshbluConfig.putIntOrIgnore("port", 443);
meshblu.setCredentials(meshbluConfig);
```

- **Is Registered:**

Returns true or false whether or not the uuid is set.

```java
meshblu.isRegistered();
```

- **Register:**

Registers device, will emit `com.octoblu.meshblukit.Meshblu.REGISTER`

```java
SaneJSONObject properties = new SaneJSONObject();
properties.putOrIgnore("type", "my-device");
meshblu.registered(properties);
```

- **Generate Token:**

Generates Token, will emit `com.octoblu.meshblukit.Meshblu.GENERATED_TOKEN`

```java
meshblu.generateToken("some-uuid");
```


- **Whoami:**

Gets authenticated device, will emit `com.octoblu.meshblukit.Meshblu.WHOAMI`

```java
meshblu.whoami();
```
- **Message:**

Message, will emit `com.octoblu.meshblukit.Meshblu.MESSAGE`


```java
SaneJSONObject message = new SaneJSONObject();
JSONArray jsonArray = new JSONArray();
jsonArray.put('*');
message.putArrayOrIgnore("devices", jsonArray);
message.putOrIgnore("topic", "test_message");
message.putOrIgnore("payload", new SaneJSONObject());
meshblu.message(message);
```

- **Update Device:**

Update device, will emit `com.octoblu.meshblukit.Meshblu.UPDATE_DEVICE`

```java
SaneJSONObject properties = new SaneJSONObject();
properties.putOrIgnore("name", "Hello");
meshblu.updateDevice("some-uuid", properties);
```

- **Claim Device:**

Claim the device through the api, will emit `com.octoblu.meshblukit.Meshblu.CLAIM_DEVICE`

```java
meshblu.claimDevice("some-uuid");
```

- **Send Data:**

Send Data, will emit `com.octoblu.meshblukit.Meshblu.SEND_DATA`

```java
SaneJSONObject data = new SaneJSONObject();
data.putOrIgnore("sensor", "hello");
meshblu.sendData(data);
```

- **Delete Device:**

Delete device, will emit `com.octoblu.meshblukit.Meshblu.DELETE_DEVICE`

```java
meshblu.deleteDevice("some-uuid");
```

- **Get Devices:**

Get devices, will emit `com.octoblu.meshblukit.Meshblu.GET_DEVICE`

```java
SaneJSONObject query = new SaneJSONObject();
query.putOrIgnore("type", "my-device");
meshblu.devices(query);
```

- **Get Data:**

Get Data, will emit `com.octoblu.meshblukit.Meshblu.GET_DATA`

```java
SaneJSONObject query = new SaneJSONObject();
query.putOrIgnore("sensor", "some-sensor");
meshblu.devices("some-uuid", query);
```

- **Get Public Key:**

Get Public Key, will emit `com.octoblu.meshblukit.Meshblu.GET_DATA`

```java
meshblu.getPublicKey("some-uuid");
```

- **Reset Token:**

Reset Token, will emit `com.octoblu.meshblukit.Meshblu.RESET_TOKEN`

```java
meshblu.resetToken("some-uuid");
```

- **Update Dangerously:**

Update Dangerously, will emit `com.octoblu.meshblukit.Meshblu.UPDATE_DEVICE`

```java
SaneJSONObject query = new SaneJSONObject();
SaneJSONObject properties = new SaneJSONObject();
properties.putOrIgnore("type", "updated-device");
query.putOrIgnore("$set", properties);
meshblu.updateDangerously("some-uuid", properties);
```
