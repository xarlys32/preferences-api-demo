package com.vw.preferences.domain.model.eventhistory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConsentHistory {
    private String id;
    private boolean enabled;
    private LocalDateTime updateTimestamp;
}
