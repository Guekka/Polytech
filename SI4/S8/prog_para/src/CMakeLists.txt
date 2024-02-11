set(ROOT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/..")

set(SOURCE_DIR "${ROOT_DIR}/src")
set(SOURCE_FILES
        "${SOURCE_DIR}/tp1.cpp")

add_executable(
        "${PROJECT_NAME}"
        ${SOURCE_FILES} "${ROOT_DIR}/.clang-format"
        "${ROOT_DIR}/.clang-tidy")

set_target_properties("${PROJECT_NAME}" PROPERTIES DEBUG_POSTFIX "d")
set_target_properties("${PROJECT_NAME}" PROPERTIES LINKER_LANGUAGE CXX)

target_compile_features("${PROJECT_NAME}" PUBLIC cxx_std_23)

target_compile_options(
        "${PROJECT_NAME}"
        PRIVATE "$<$<CXX_COMPILER_ID:AppleClang,Clang,GNU>:-fvisibility=hidden>"
        "$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")

target_compile_options("${PROJECT_NAME}"
        PUBLIC "$<$<CXX_COMPILER_ID:MSVC>:/Zc:__cplusplus>")

find_package(OpenMP REQUIRED)
target_link_libraries("${PROJECT_NAME}" PUBLIC OpenMP::OpenMP_CXX)
