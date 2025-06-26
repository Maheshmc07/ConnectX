package org.mc.connectx.Utils;


import org.mc.connectx.Entities.ScheduleEnity;
import org.mc.connectx.Repositories.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

@Service
public class BirthdayScheduleConfig {
    @Autowired
    BirtdayService birtdayService;


    @Autowired
    private ScheduleRepo  schedulerepo;


    ThreadPoolTaskScheduler taskExecutor = new  ThreadPoolTaskScheduler();

    private ScheduledFuture<?> scheduledFuture;

    BirthdayScheduleConfig(){

        taskExecutor.initialize();
    }

    public void UpdateCronTask(String TaskName,String CronExp){

        ScheduleEnity scheduleEnity=schedulerepo.findByTaskName(TaskName).orElse(new ScheduleEnity());
        scheduleEnity.setCronExpression(CronExp);
        scheduleEnity.setTaskName(TaskName);

        schedulerepo.save(scheduleEnity);

        RestartCron(CronExp);



    }

    public void RestartCron(String CronExp){

        if(scheduledFuture!=null){

            scheduledFuture.cancel(true);
        }

        scheduledFuture=taskExecutor.schedule(birtdayService::WishHappyBirthday,new CronTrigger(CronExp));




    }




}
