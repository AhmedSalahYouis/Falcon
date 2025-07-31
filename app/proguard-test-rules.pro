# Test-specific ProGuard rules
# This file is used for testing builds to prevent obfuscation issues

# Disable obfuscation for testing
-dontobfuscate
-dontoptimize
-dontpreverify

# Keep all classes for testing
-keep class com.salah.falcon.** { *; }
-keep class com.apollographql.** { *; }
-keep class org.koin.** { *; }
-keep class androidx.** { *; }
-keep class kotlinx.** { *; }
-keep class io.mockk.** { *; }
-keep class kotlin.reflect.** { *; }

# Keep all test classes
-keep class * extends androidx.test.ext.junit.runners.AndroidJUnit4 { *; }
-keep class * extends org.junit.Test { *; }

# Keep all annotations
-keep @interface * { *; }

# Keep all attributes
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions
-keepattributes SourceFile,LineNumberTable 