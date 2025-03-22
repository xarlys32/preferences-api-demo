package com.vw.preferences.infrastructure.rest.event.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConsentDTO {
    private String id;
    private boolean enabled;
    private LocalDateTime updateTimestamp;
}
