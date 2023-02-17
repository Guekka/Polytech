cmake_minimum_required(VERSION 3.24)
project(TD5 C)


set(CMAKE_C_STANDARD 99)
set(CMAKE_C_STANDARD_REQUIRED ON)
set(CMAKE_C_EXTENSIONS OFF)
set(CMAKE_CXX_COMPILER_ID GNU)


# Add project_options v0.26.1
# https://github.com/aminya/project_options
# Change the version in the following URL to update the package (watch the releases of the repository for future updates)
include(FetchContent)
FetchContent_Declare(_project_options URL https://github.com/aminya/project_options/archive/refs/tags/v0.26.1.zip)
FetchContent_MakeAvailable(_project_options)
include(${_project_options_SOURCE_DIR}/Index.cmake)

project_options(
        ENABLE_SANITIZER_ADDRESS ON
        ENABLE_SANITIZER_UNDEFINED_BEHAVIOR ON
        WARNINGS_AS_ERRORS ON
)

add_library(TD6 STATIC vector.h)
set_target_properties(TD6 PROPERTIES LINKER_LANGUAGE C)

add_executable(stack 1-stack.c)
add_executable(linked-list 2-linked-list.c)
add_executable(stding-to-str 3-stdin-to-str.c)
target_link_libraries(stding-to-str TD6)
add_executable(btree 4-btree.c)

get_target_property(options linked-list COMPILE_OPTIONS)
message(STATUS "Compile options for linked-list: ${options}")