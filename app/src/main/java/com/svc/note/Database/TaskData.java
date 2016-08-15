package com.svc.note.Database;

/**
 * Created by PRIYASH on 03-07-2016.
 */
public class TaskData {

    private static final String TAG = TaskData.class.getName();

    private int TASK_ID;
    private long ROW_ID;
    private String TASK;
    private String TASK_DESCRIPTION;
    private String TASK_PRIORITY;
    private String TASK_DUE_DATE;
    private String TASK_REMINDER;
    private String TASK_COMMENTS;
    private String TASK_LABEL;
    private String TASK_NOTIFICATION;
    private String TASK_TAGGED_PEOPLE;


    public void setROW_ID(long id) {
        this.ROW_ID = id;
    }

    public long getROW_ID() {
        return ROW_ID;
    }


    public void setTaskId(int ID) {
        this.TASK_ID = ID;
    }

    public int getTASK_ID() {
        return TASK_ID;
    }

    public void setTASK(String task) {
        this.TASK = task;
    }

    public String getTASK() {
        return TASK;
    }


    public void setTASK_DESCRIPTION(String description) {
        this.TASK_DESCRIPTION = description;
    }

    public String getTASK_DESCRIPTION() {
        return TASK_DESCRIPTION;
    }


    public void setTASK_PRIORITY(String priority) {
        this.TASK_PRIORITY = priority;
    }

    public String getTASK_PRIORITY() {
        return TASK_PRIORITY;
    }


    public void setTASK_DUE_DATE(String due_date) {
        this.TASK_DUE_DATE = due_date;
    }

    public String getTASK_DUE_DATE() {
        return TASK_DUE_DATE;
    }


    public void setTASK_REMINDER(String reminder) {
        this.TASK_REMINDER = reminder;
    }

    public String getTASK_REMINDER() {
        return TASK_REMINDER;
    }


    public void setTASK_COMMENTS(String comments) {
        this.TASK_DUE_DATE = comments;
    }

    public String getTASK_COMMENTS() {
        return TASK_DUE_DATE;
    }


    public void setTASK_NOTIFICATION(String task_notification)
    {
        this.TASK_NOTIFICATION = task_notification;
    }

    public String getTASK_NOTIFICATION(){return TASK_NOTIFICATION;}

    public void setTASK_LABEL(String label)
    {
        this.TASK_LABEL = label;
    }

    public String getTASK_LABEL(){return TASK_LABEL;}

    public void setTASK_TAGGED_PEOPLE(String tagged_people)
    {
        this.TASK_TAGGED_PEOPLE = tagged_people;
    }

    public String getTASK_TAGGED_PEOPLE(){return TASK_TAGGED_PEOPLE;}

}
