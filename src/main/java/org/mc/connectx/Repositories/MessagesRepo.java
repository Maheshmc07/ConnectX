package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MessagesRepo extends JpaRepository<Messages, UUID> {
}
