package org.mc.connectx.Entities;


import jakarta.persistence.*;
import lombok.Data;
import org.mc.connectx.AllEnums.RequestStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;


    @Enumerated(EnumType.STRING)
    public RequestStatus  status;

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime creationDate;

    @LastModifiedDate
    public LocalDateTime modificationDate;


}
