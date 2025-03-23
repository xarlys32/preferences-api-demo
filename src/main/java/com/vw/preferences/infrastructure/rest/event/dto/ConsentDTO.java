package com.vw.preferences.infrastructure.rest.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ConsentDTO {
    private String id;
    private boolean enabled;
    private Date updateTimestamp;
}
