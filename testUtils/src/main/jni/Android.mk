LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_CFLAGS += -pie -fPIE
LOCAL_LDFLAGS += -pie -fPIE

LOCAL_MODULE := test_hello_world
LOCAL_SRC_FILES =: test_hello_world.cpp
include $(BUILD_EXECUTABLE)
