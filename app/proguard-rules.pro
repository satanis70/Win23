-keep class kotlinx.coroutines.android.** {*;}
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-keep class info.jasonni.retrofitdemo.bean.** { *; }
-dontnote okhttp3.**, okio.**, retrofit2.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }