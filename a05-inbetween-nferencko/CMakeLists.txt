# CMakeLists.txt at the root of the project

cmake_minimum_required(VERSION 3.5)

# Name SampleClass needs to match one of the Source names in Sources.
set(This SampleClass)

project(${This} C CXX)
set(CMAKE_C_STANDARD 99)
set (CMAKE_CXX_STANDARD 23)
set (CMAKE_POSITION_INDEPENDENT_CODE ON)


enable_testing()
add_subdirectory(googletest)

set (Headers
    include/SampleClass.h
    include/Card.h
    include/Hand.h
    include/InBetweenHand.h
    include/Deck.h
    include/Bank.h
    include/InBetweenPlayer.h
    include/AIConservativeInBetweenPlayer.h
)

set (Sources
    src/SampleClass.cpp
    src/Card.cpp
    src/Hand.cpp
    src/InBetweenHand.cpp
    src/Deck.cpp
    src/Bank.cpp
    src/InBetweenPlayer.cpp
    src/AIConservativeInBetweenPlayer.cpp
)

add_library(${This} STATIC ${Sources} ${Headers})

add_subdirectory(test)