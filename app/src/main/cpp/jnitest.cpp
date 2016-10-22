#include <jni.h>

namespace oiri {
extern "C"
{
JNIEXPORT jstring JNICALL
        Java_com_github_kimikage_oiri_MainActivity_jnitest(JNIEnv *env, jclass type);
}

JNIEXPORT jstring JNICALL
Java_com_github_kimikage_oiri_MainActivity_jnitest(JNIEnv *env, jclass type) {
    return env->NewStringUTF(u8"JNI");
}

}