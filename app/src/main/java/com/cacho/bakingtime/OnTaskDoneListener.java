package com.cacho.bakingtime;

public interface OnTaskDoneListener {

    void onTaskDone(String responseData);

    void onError();
}
