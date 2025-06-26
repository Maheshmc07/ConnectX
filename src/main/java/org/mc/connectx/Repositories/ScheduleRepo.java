package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.ScheduleEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ScheduleRepo extends  JpaRepository<ScheduleEnity,Long> {

    public  Optional<ScheduleEnity> findByTaskName(String task);
}
