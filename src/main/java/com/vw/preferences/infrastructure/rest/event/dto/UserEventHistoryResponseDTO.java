package com.vw.preferences.infrastructure.rest.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserEventHistoryResponseDTO {
    private String userId;
    private List<ConsentDTO> consents;
}
