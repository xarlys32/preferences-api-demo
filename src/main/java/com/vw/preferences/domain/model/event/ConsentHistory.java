package com.vw.preferences.domain.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentHistory {
    private String consentId;
    private boolean enabled;
    private Date updateTimestamp;

}
