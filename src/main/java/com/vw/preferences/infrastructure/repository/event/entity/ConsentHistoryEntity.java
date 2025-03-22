package com.vw.preferences.infrastructure.repository.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Date;

@Document
@AllArgsConstructor
@Data
public class ConsentHistoryEntity {
    @Field(name = "consent_id")
    private String consentId;
    @Field(name = "enabled")
    private boolean enabled;
    @Field(name = "update_time")
    private Date updateTimestamp;
}
