package com.vw.preferences.infrastructure.repository.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@Data
public class ConsentHistoryEntity {
    private String consentId;
    private boolean enabled;
    private LocalDateTime updateTimestamp;
}
