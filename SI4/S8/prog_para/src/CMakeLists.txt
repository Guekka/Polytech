set(ROOT_DIR "${CMAKE_CURRENT_SOURCE_DIR}/..")

set(SOURCE_DIR "${ROOT_DIR}/src")

function(make_tp target_name sources)
    add_executable(
            "${target_name}"
            "${SOURCE_DIR}/utils.hpp"
            "${sources}"
            "${ROOT_DIR}/.clang-format"
            "${ROOT_DIR}/.clang-tidy")

    set_target_properties("${target_name}" PROPERTIES DEBUG_POSTFIX "d")
    set_target_properties("${target_name}" PROPERTIES LINKER_LANGUAGE CXX)

    target_compile_features("${target_name}" PUBLIC cxx_std_23)

    target_compile_options(
            "${target_name}"
            PRIVATE "$<$<CXX_COMPILER_ID:AppleClang,Clang,GNU>:-fvisibility=hidden>"
            "$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")

    target_compile_options("${target_name}"
            PUBLIC "$<$<CXX_COMPILER_ID:MSVC>:/Zc:__cplusplus>")

    find_package(OpenMP REQUIRED)
    target_link_libraries("${target_name}" PUBLIC OpenMP::OpenMP_CXX)
endfunction()

make_tp(tp1 tp1.cpp)
make_tp(tp2 tp2-quicksort-seq.cpp)
