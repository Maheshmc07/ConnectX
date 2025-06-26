package org.mc.connectx.DTO;

public class ScheduleDTO {
    public String getCronExpression() {
        return CronExpression;
    }

    public void setCronExpression(String cronExpression) {
        CronExpression = cronExpression;
    }

    public String getTaskName() {
        return TaskName;
    }

    public void setTaskName(String taskName) {
        TaskName = taskName;
    }

    private String TaskName;
    private String CronExpression;


}
