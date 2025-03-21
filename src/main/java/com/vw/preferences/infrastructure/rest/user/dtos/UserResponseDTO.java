package com.vw.preferences.infrastructure.rest.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String id;
    private String email;
    private List<ConsentDTO> consents;
}
