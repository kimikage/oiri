/*
 * Copyright (c) 2016 Oiri Project
 *
 * This software is distributed under an MIT-style license.
 * See LICENSE file for more information.
 */

#ifndef OIRI_LOG_H
#define OIRI_LOG_H

#include <android/log.h>

namespace oiri {

class Log {
private:
    static constexpr const char *MODULE_NAME = "OIRI";
private:
    Log() = delete;

public:
    static inline void V(const char *text) {
#ifndef NDEBUG
        __android_log_write(ANDROID_LOG_VERBOSE, MODULE_NAME, text);
#endif
    }

    static inline void D(const char *text) {
#ifndef NDEBUG
        __android_log_write(ANDROID_LOG_DEBUG, MODULE_NAME, text);
#endif
    }

    static inline void I(const char *text) {
        __android_log_write(ANDROID_LOG_INFO, MODULE_NAME, text);
    }

    static inline void W(const char *text) {
        __android_log_write(ANDROID_LOG_WARN, MODULE_NAME, text);
    }

    static inline void E(const char *text) {
        __android_log_write(ANDROID_LOG_ERROR, MODULE_NAME, text);
    }

    static inline void F(const char *text) {
        __android_log_write(ANDROID_LOG_FATAL, MODULE_NAME, text);
    }

    template<typename ... Args>
    static inline void V(const char *fmt, Args const &... args) {
#ifndef NDEBUG
        __android_log_print(ANDROID_LOG_VERBOSE, MODULE_NAME, fmt, args ...);
#endif
    }

    template<typename ... Args>
    static inline void D(const char *fmt, Args const &... args) {
#ifndef NDEBUG
        __android_log_print(ANDROID_LOG_DEBUG, MODULE_NAME, fmt, args ...);
#endif
    }

    template<typename ... Args>
    static inline void I(const char *fmt, Args const &... args) {
        __android_log_print(ANDROID_LOG_INFO, MODULE_NAME, fmt, args ...);
    }

    template<typename ... Args>
    static inline void W(const char *fmt, Args const &... args) {
        __android_log_print(ANDROID_LOG_WARN, MODULE_NAME, fmt, args ...);
    }

    template<typename ... Args>
    static inline void E(const char *fmt, Args const &... args) {
        __android_log_print(ANDROID_LOG_ERROR, MODULE_NAME, fmt, args ...);
    }

    template<typename ... Args>
    static inline void F(const char *fmt, Args const &... args) {
        __android_log_print(ANDROID_LOG_FATAL, MODULE_NAME, fmt, args ...);
    }
};
}

#endif //OIRI_LOG_H
