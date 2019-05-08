# CoroutinePermissions

CoroutinePermissions is library that allows you to ask for android runtime permissions easily. 
API is very simple and can be called from any context (not only activity).
Also library will take care of few problems:
1. If you ask for permission A few times, dialog will be shown only once
2. It allows you to control callbacks after configuration change (when permission dialog isn't dismissed)
3. It allows you to ask for permission from ViewModels, or event static objects

# Possibilities
You can use one of few predefined permissions, or just use string version.

## Static
Static version will implement SuspendPermission interface
```
interface SuspendPermissions {
    suspend fun request(permission: String): Boolean

    suspend fun requestLocation(): Boolean

    suspend fun requestCamera(): Boolean

    suspend fun requestExternalStorageRead(): Boolean

    suspend fun requestExternalStorageWrite(): Boolean
}
```

## OnActivity 
Activity version implements SuspendPermissions and SuspendActivityPermissions interface

interface SuspendActivityPermissions: SuspendPermissions {

    suspend fun getRequestResult(permission: String): PermissionRequestState

    suspend fun getRequestLocation(): PermissionRequestState

    suspend fun getRequestCamera(): PermissionRequestState

    suspend fun getRequestExternalStorageRead(): PermissionRequestState

    suspend fun getRequestExternalStorageWrite(): PermissionRequestState


}


# Usage

## Static

1. Get permission instance:

```
val permissions = CoroutinePermissions.getInstance()
```
2. Now just execute one of request functions in coroutine scope:

```
 launch {
     val result:Boolean = permissions.requestExternalStorageRead()
    }
```

That's it, result is a Boolean value which will tell you if your permission was granted.

## Activity
On Activity context thing are getting complicated, because we have to handle configuration changes and activity recreation.

1. Create activity permission instance 
```
val permissions = CoroutinePermissions.createInstanceForActivity(activity)
```

### Option A, request in "starting" function

2.Execute request function in onCreate, or other "starting" function

```
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch {
            val result: Boolean = permissions.requestExternalStorageRead()
        }

    }
```
3.  After configuration change and activity recreation while permission dialog is still visible, calling the same function again (because onCreate will be called again) won't execute next permission request, but will connect to one which is already present. 

### Option B, request after some event, or user interaction

2. Execute request function after some event, for example click
```
button.setOnClickListener{
            launch {
                val result = permissions.requestExternalStorageRead()
            }
        }
```
3. Execute additional function, which will reassembly callback to permission dialog, after configuration change:
```
  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch {
            val result: PermissionRequestState = permissions.getRequestExternalStorageRead()
        }

    }
```
As you can see, it doesn't return Boolean, but PermissionRequestState, because it can be:
Granted - user granted permission after configuration change with visible dialog
Denied - user denied permission after configuration change with visible dialog
No_Request_Pending - after configuration change there was no permission dialog to connect with

# Examples
You can find examples for ActivityPermission and StaticPermission in app module on this repository.
Anyway, if you can, just use Static version with ViewModels. It is way easier.

# Logging
If you want to control logging, you can use functions in CoroutinePermission object:
```
object CoroutinePermissions {

    fun disableLogging() {
        Permissions.disableLogging()
    }

    fun enableLogging() {
        Permissions.enableLogging()
    }
}
```

# Adding library to your project 

Add it to your main build.gradle:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
and into module build.gradle:

```gradle
dependencies {
    implementation "com.github.coneys:coroutinePermissions:{latest version}"
}
```
