

cmake_minimum_required(VERSION 3.4.1)

aux_source_directory(bzip2  SOURCES)

add_library(
        native-lib
        SHARED
        native-lib.cpp
        bspatch.c
        ${SOURCES})


include_directories(bzip2)

find_library(
        log-lib
        log)


target_link_libraries(
        native-lib
        ${log-lib})