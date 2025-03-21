package com.vw.preferences.infrastructure.repository.eventhistory.adapter;

import com.vw.preferences.domain.model.eventhistory.ConsentHistory;
import com.vw.preferences.domain.model.eventhistory.UserEventHistory;
import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.infrastructure.repository.eventhistory.entity.ConsentHistoryEntity;
import com.vw.preferences.infrastructure.repository.eventhistory.entity.UserEventHistoryEntity;
import com.vw.preferences.infrastructure.repository.user.entity.ConsentEntity;
import com.vw.preferences.infrastructure.repository.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public UserEventHistory fromUserToDom(User user) {
        return new UserEventHistory(user.getId(), user.getConsents().stream().map(consent -> fromConsentToDom(consent)).toList());
    }

    private static ConsentHistoryEntity consentToDocument(ConsentHistory consentHistory) {
        return new ConsentHistoryEntity(consentHistory.getId(), consentHistory.isEnabled(),
                consentHistory.getUpdateTimestamp());
    }

    private static ConsentHistory consentToDom(ConsentHistoryEntity consentHistoryEntity) {
        return new ConsentHistory(consentHistoryEntity.getId(), consentHistoryEntity.isEnabled(),
                consentHistoryEntity.getUpdateTimestamp());
    }

    private static ConsentHistory fromConsentToDom(Consent consent) {
        return new ConsentHistory(consent.getConsentId(), consent.isEnabled(), LocalDate.now());
    }
}
