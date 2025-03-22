package com.vw.preferences.infrastructure.repository.event.adapter;

import com.vw.preferences.domain.model.event.ConsentHistory;
import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.infrastructure.repository.event.entity.ConsentHistoryEntity;
import com.vw.preferences.infrastructure.repository.event.entity.UserEventHistoryEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserEventHistoryEntityMapper {

    public UserEventHistory toDom(UserEventHistoryEntity userEventEntity) {
        List<ConsentHistory> consentList = userEventEntity.getConsentHistoryList().stream()
                .map(UserEventHistoryEntityMapper::consentToDom)
                .toList();

        return new UserEventHistory(userEventEntity.getUserId(), consentList);
    }

    public UserEventHistoryEntity toDocument(UserEventHistory userEvent) {
        List<ConsentHistoryEntity> consentEntities = userEvent.getConsentHistoryList().stream()
                .map(UserEventHistoryEntityMapper::consentToDocument)
                .toList();

        return new UserEventHistoryEntity(userEvent.getUserId(), consentEntities);
    }

    public ConsentHistory fromPostEventToDom(Consent consent) {
        return new ConsentHistory(consent.getConsentId(), consent.getEnabled(), LocalDate.now().atStartOfDay());
    }

    private static ConsentHistoryEntity consentToDocument(ConsentHistory consentHistory) {
        return new ConsentHistoryEntity(consentHistory.getConsentId(), consentHistory.isEnabled(),
                consentHistory.getUpdateTimestamp());
    }

    private static ConsentHistory consentToDom(ConsentHistoryEntity consentHistoryEntity) {
        return new ConsentHistory(consentHistoryEntity.getConsentId(), consentHistoryEntity.isEnabled(),
                consentHistoryEntity.getUpdateTimestamp());
    }

    private static ConsentHistory fromConsentToDom(Consent consent) {
        return new ConsentHistory(consent.getConsentId(), consent.getEnabled(), LocalDate.now().atStartOfDay());
    }
}
