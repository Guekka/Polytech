cmake_minimum_required (VERSION 3.8)

project ("td1b" LANGUAGES C)

# not the cleanest way, but allows us have same output dir for exe, lib and dll
set (CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})
set (CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR})

# Include sub-projects.
add_subdirectory ("Bibliotheques")
add_subdirectory("Exe_DynLink")
add_subdirectory("Exe_DynLoad")