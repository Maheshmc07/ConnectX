package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.RefreshTokenEnity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEnity, Integer> {

    Optional<RefreshTokenEnity> findByToken(String token);
}
