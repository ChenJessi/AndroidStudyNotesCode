cmake_minimum_required(VERSION 3.4.1)


#引入breakpad的头文件（api的定义）
include_directories(breakpad/src breakpad/src/common/android/include)

#引入 breakpad 的 cmakelist，执行并生成 libbreakpad.a （api 的实现， 类似 java 的jar包）
add_subdirectory(breakpad)

# 生成 libbugly.so  源码是 ndk_crash.cpp (我们自己的源码，要使用breakpad)
add_library(bugly SHARED ndk_crash.cpp)

target_link_libraries(
        bugly
        breakpad   # 引入 breakpad 库文件(api实现)
        log
)