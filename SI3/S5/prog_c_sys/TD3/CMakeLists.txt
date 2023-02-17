project(TD3 LANGUAGES C)
cmake_minimum_required(VERSION 3.0)

set(CMAKE_C_STANDARD 99)

add_executable(search 1-search.c)
add_executable(palindrome 2-palindrome.c)
add_executable(stdlib 3-stdlib.c)
add_executable(suppress 4-suppress-char.c)
add_executable(fgrep 5-fgrep.c)
add_executable(suppression 6-suppression.c)