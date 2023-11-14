cmake_minimum_required(VERSION 3.15)
project(rsa LANGUAGES C)

find_package(OpenSSL REQUIRED)

add_library(rsa STATIC rsa.h rsa.c)

add_executable(task1 task1.c)
target_link_libraries(task1 OpenSSL::SSL rsa)

add_executable(task2 task2.c)
target_link_libraries(task2 OpenSSL::SSL rsa)

add_executable(task3 task3.c)
target_link_libraries(task3 OpenSSL::SSL rsa)

add_executable(task4 task4.c)
target_link_libraries(task4 OpenSSL::SSL rsa)

add_executable(task5 task5.c)
target_link_libraries(task5 OpenSSL::SSL rsa)