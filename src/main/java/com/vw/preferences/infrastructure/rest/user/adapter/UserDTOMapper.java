package com.vw.preferences.infrastructure.rest.user.adapter;

import com.vw.preferences.domain.exception.PreferencesNotFoundException;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.infrastructure.rest.user.dtos.ConsentDTO;
import com.vw.preferences.infrastructure.rest.user.dtos.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    public UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            throw new PreferencesNotFoundException("Email not found");
        }
        return new UserResponseDTO(user.getUserId(), user.getEmail(),
                user.getConsents().stream().map(UserDTOMapper::toConsentDTO).toList());
    }

    private static ConsentDTO toConsentDTO(Consent consent) {
        return new ConsentDTO(consent.getConsentId(), consent.getEnabled());
    }
}
