package com.vw.preferences.infrastructure.rest.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String email;
    private List<ConsentDTO> consents;
}
