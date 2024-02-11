cmake_minimum_required(VERSION 3.19)

project(
  prog_para
  VERSION 0.1.0
  LANGUAGES CXX)

set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

if("${PROJECT_SOURCE_DIR}" STREQUAL "${PROJECT_BINARY_DIR}")
  message(FATAL_ERROR "in-source builds are not allowed")
endif()

add_subdirectory(src)
