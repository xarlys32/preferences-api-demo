package com.vw.preferences.infrastructure.repository.eventhistory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@Data
public class ConsentHistoryEntity {
    private String id;
    private boolean enabled;
    private LocalDateTime updateTimestamp;
}
