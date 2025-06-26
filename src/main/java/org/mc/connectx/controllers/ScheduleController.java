package org.mc.connectx.controllers;

import org.mc.connectx.DTO.ScheduleDTO;
import org.mc.connectx.Utils.BirthdayScheduleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ScheduleController {
    @Autowired
    BirthdayScheduleConfig  birthdayScheduleConfig;

    @PostMapping("/UpdateCrons")
    public String UpdateCrons(@RequestBody ScheduleDTO  scheduleDTO) {
        birthdayScheduleConfig.UpdateCronTask(scheduleDTO.getTaskName(), scheduleDTO.getCronExpression());


        return  "Hello World";
    }
}
