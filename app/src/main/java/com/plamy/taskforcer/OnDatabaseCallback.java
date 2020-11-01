package com.plamy.taskforcer;

import java.util.ArrayList;

public interface OnDatabaseCallback {
    public void insert(String title, String startDate, String endDate, String content,
                       int completedFlag);
    public ArrayList<TaskInfo> selectAll();
}
