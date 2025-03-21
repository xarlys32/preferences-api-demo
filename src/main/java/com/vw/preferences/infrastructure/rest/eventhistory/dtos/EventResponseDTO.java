package com.vw.preferences.infrastructure.rest.eventhistory.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventResponseDTO {
    private String id;
    private String email;
    private List<ConsentDTO> consents;
}
