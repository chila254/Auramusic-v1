cmake_minimum_required(VERSION 3.22.1)

project("vibra_fp")

# Stub implementation for vibra_fp library
# This provides a dummy implementation that returns an empty signature

add_library(vibra_fp SHARED
    vibra_fp.cpp
)

target_compile_options(vibra_fp PRIVATE
    -Wall
    -Wextra
    -Werror
    -O2
)

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
