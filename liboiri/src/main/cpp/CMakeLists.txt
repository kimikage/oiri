cmake_minimum_required(VERSION 3.4.1)

set(distribution_DIR ${CMAKE_SOURCE_DIR}/../../../../distribution)

add_library(ngspice SHARED IMPORTED)
set_target_properties(ngspice PROPERTIES IMPORTED_LOCATION
    ${distribution_DIR}/ngspice/lib/${ANDROID_ABI}/libngspice.so)

add_library(oiri SHARED
            oiri.cpp
            ngspice.cpp)

target_include_directories(oiri PRIVATE
                           ${distribution_DIR}/ngspice/include)

target_link_libraries(oiri
                      ngspice
                      log)
