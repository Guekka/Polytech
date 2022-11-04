cmake_minimum_required(VERSION 3.22)
project(progc_td2 C)

set(CMAKE_C_STANDARD 99)

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall")

add_executable(comptage 1-comptage.c)
add_executable(wc 2-wc.c)
add_executable(insertion 3-insertion.c)
add_executable(dump_hexa 4-dump_hexa.c)