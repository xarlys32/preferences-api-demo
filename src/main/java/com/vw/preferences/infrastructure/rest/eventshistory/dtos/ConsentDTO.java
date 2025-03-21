package com.vw.preferences.infrastructure.rest.eventshistory.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsentDTO {
    private String id;
    private boolean enabled;
}
