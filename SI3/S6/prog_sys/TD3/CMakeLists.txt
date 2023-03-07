cmake_minimum_required(VERSION 3.0)

project(process C)

add_executable(multiple_fork multiple_fork.c)
add_executable(zombie zombie.c)
add_executable(orphelin orphelin.c)
add_executable(exec_prop_aux exec_prop_aux.c)
add_executable(exec_prop exec_prop.c)
add_dependencies(exec_prop exec_prop_aux)

add_executable(shell_exec shell_exec.c)
add_executable(shell_system shell_system.c)