package com.vw.preferences.infrastructure.rest.user.adapter;

import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.infrastructure.rest.user.dtos.ConsentDTO;
import com.vw.preferences.infrastructure.rest.user.dtos.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    public UserResponseDTO toResponseDTO(User user) {

        return new UserResponseDTO(user.getId(), user.getEmail(),
                user.getConsents().stream().map(UserDTOMapper::toConsentDTO).toList());
    }

    private static ConsentDTO toConsentDTO(Consent consent) {
        return new ConsentDTO(consent.getId(), consent.isEnabled());
    }
}
