package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.ReportEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReportRepo extends JpaRepository<ReportEnity, UUID> {
}
