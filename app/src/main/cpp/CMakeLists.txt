cmake_minimum_required(VERSION 3.4.1)

add_library(jnitest SHARED
            jnitest.cpp)

target_link_libraries(jnitest
                      log)
