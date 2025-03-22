package com.vw.preferences.infrastructure.rest.event.adapter;

import com.vw.preferences.domain.model.event.ConsentHistory;
import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.infrastructure.rest.event.dtos.ConsentDTO;
import com.vw.preferences.infrastructure.rest.event.dtos.UserEventHistoryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UseEventHistoryDTOMapper {
    public UserEventHistoryResponseDTO toResponseDTO(UserEventHistory userEvent) {

        return new UserEventHistoryResponseDTO(userEvent.getUserId(),
                userEvent.getConsentHistoryList().stream().map(UseEventHistoryDTOMapper::toConsentDTO).toList());
    }

    private static ConsentDTO toConsentDTO(ConsentHistory consent) {
        return new ConsentDTO(consent.getConsentId(), consent.isEnabled(), consent.getUpdateTimestamp());
    }
}
