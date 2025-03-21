package com.vw.preferences.infrastructure.rest.eventshistory.adapter;

import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.infrastructure.rest.eventshistory.dtos.ConsentDTO;
import com.vw.preferences.infrastructure.rest.eventshistory.dtos.EventResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PreferencesDTOMapper {
    public EventResponseDTO toResponseDTO(Event preferences) {

        return new EventResponseDTO(preferences.getUser().getId(),preferences.getUser().getEmail(),
                preferences.getConsents().stream().map(PreferencesDTOMapper::toConsentDTO).toList());
    }

    private static ConsentDTO toConsentDTO(Consent consent) {
        return new ConsentDTO(consent.getId(), consent.isEnabled());
    }
}
