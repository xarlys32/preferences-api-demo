package com.vw.preferences.infrastructure.repository.user.adapter;

import com.vw.preferences.domain.model.user.Consent;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.infrastructure.repository.user.entity.ConsentEntity;
import com.vw.preferences.infrastructure.repository.user.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserEntityMapper {

        public User toDom(UserEntity userEntity) {
            List<Consent> consentList = userEntity.getConsents().stream()
                    .map(UserEntityMapper::consentToDom)
                    .toList();
            return new User(userEntity.getUserId(), userEntity.getEmail(), consentList);
        }

        public UserEntity toDocument(User user) {
            List<ConsentEntity> consentEntities = user.getConsents().stream()
                    .map(UserEntityMapper::consentToDocument)
                    .toList();
            return new UserEntity(user.getUserId(), user.getEmail(), consentEntities);
        }

        private static Consent consentToDom(ConsentEntity consentEntity) {
            return new Consent(consentEntity.getConsentId(), consentEntity.isEnabled());
        }

        private static ConsentEntity consentToDocument(Consent consent) {
            return new ConsentEntity(consent.getConsentId(), consent.getEnabled());
        }
}
