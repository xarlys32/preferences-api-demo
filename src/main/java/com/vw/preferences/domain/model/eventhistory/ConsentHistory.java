package com.vw.preferences.domain.model.eventhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentHistory {
    private String consentId;
    private boolean enabled;
    private LocalDateTime updateTimestamp;
}
