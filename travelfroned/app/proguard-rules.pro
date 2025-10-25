# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ==================== Retrofit ====================
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

# ==================== Moshi ====================
-keepclassmembers class ** {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *

# Keep data classes used with Moshi (ajusta el paquete según tu proyecto)
-keep class com.example.travelmarket.logic.data.models.** { *; }
-keepclassmembers class com.example.travelmarket.logic.data.models.** { *; }

# Kotlin Metadata
-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# ==================== OkHttp ====================
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# ==================== Coroutines ====================
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ==================== Hilt ====================
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.** { *; }
-keepclassmembers class * {
    @javax.inject.* <fields>;
    @javax.inject.* <methods>;
}
-keepclassmembers class * {
    @dagger.* <fields>;
    @dagger.* <methods>;
}

# ==================== Compose ====================
-dontwarn androidx.compose.**
-keep class androidx.compose.** { *; }

# ==================== General Android ====================
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# ==================== Models/DTOs ====================
# Mantener todos los modelos de datos para serialización
-keep class com.example.travelmarket.logic.domain.models.** { *; }
-keepclassmembers class com.example.travelmarket.logic.domain.models.** { *; }

# ==================== Enum ====================
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# ==================== Parcelable ====================
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
