package com.plamy.taskforcer;

public class TaskInfo {
    String title;
    String startDate;
    String endDate;
    String content;
    int completedFlag;

    public TaskInfo(String title, String startDate, String endDate, String content, int completedFlag) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.completedFlag = completedFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCompletedFlag() {
        return completedFlag;
    }

    public void setCompletedFlag(int completedFlag) {
        this.completedFlag = completedFlag;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", content='" + content + '\'' +
                ", completedFlag=" + completedFlag +
                '}';
    }
}
