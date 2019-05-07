## CoroutinePermissions

CoroutinePermissions is library that allows you to ask for android runtime permissions easily. 
API is very simple and can be called from any context (not only activity).
Also library will take care of few problems:
1. If you ask for permission A few times, dialog will be shown only once
2. If you use activity context, library won't allow to screen rotation, to avoid memory leak with permission dialog

## Possibilities
You can use one of few predefined permissions, or just use string version.
```
interface SuspendPermissions {
    suspend fun request(permission: String): Boolean

    suspend fun requestLocation(): Boolean

    suspend fun requestCamera(): Boolean

    suspend fun requestExternalStorageRead(): Boolean

    suspend fun requestExternalStorageWrite(): Boolean
}
```

## Usage

1. Permissions instance can by creating CoroutinePermissions instance:

```
class CoroutinePermissions internal constructor(private val context: Context) :
    SuspendPermissions {

    constructor(activity: FragmentActivity) : this(activity as Context)
    constructor(fragment: Fragment) : this(fragment.requireContext())
    constructor(application: Application) : this(application.applicationContext)

}
```
2. Now just execute one of request functions in coroutine scope:

```
 launch {
                val result = permissions.requestExternalStorageRead()
                println(result)
            }
```
