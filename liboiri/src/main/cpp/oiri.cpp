/*
 * Copyright (c) 2016 Oiri Project
 *
 * This software is distributed under an MIT-style license.
 * See LICENSE file for more information.
 */

#include <jni.h>
#include "ngspice.h"

namespace oiri {
extern "C"
{
JNIEXPORT jstring JNICALL
        Java_com_github_kimikage_oiri_MainActivity_jnitest(JNIEnv *env, jclass type);
}

JNIEXPORT jstring JNICALL
Java_com_github_kimikage_oiri_MainActivity_jnitest(JNIEnv *env, jclass type) {
    Ngspice &spice = Ngspice::GetInstance();
    spice.Initialize();
    spice.Command("circbyline V1 1 0 sin(0.5 1 60)");
    spice.Command("circbyline R1 1 2 10k");
    spice.Command("circbyline C1 2 0 1u IC=0.5V");
    spice.Command("circbyline .TRAN 10u 0.1 uic");
    spice.Command("circbyline .end");
    spice.SetBrakePoint(1e-3);
    spice.Run();
    return env->NewStringUTF(u8"JNI");
}

}