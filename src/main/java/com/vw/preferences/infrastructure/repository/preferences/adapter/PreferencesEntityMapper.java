package com.vw.preferences.infrastructure.repository.preferences.adapter;

import com.vw.preferences.domain.model.preferences.Consent;
import com.vw.preferences.domain.model.preferences.Preferences;
import com.vw.preferences.domain.model.preferences.User;
import com.vw.preferences.infrastructure.repository.preferences.entity.ConsentEntity;
import com.vw.preferences.infrastructure.repository.preferences.entity.PreferencesEntity;
import com.vw.preferences.infrastructure.repository.preferences.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PreferencesEntityMapper {

        public Preferences toDom(PreferencesEntity preferencesDoc) {
            User user = userToDom(preferencesDoc.getUserEntity());
            List<Consent> consentList = preferencesDoc.getConsents().stream()
                    .map(PreferencesEntityMapper::consentToDom)
                    .toList();
            return new Preferences(user, consentList);
        }

        public PreferencesEntity toDocument(Preferences preferences) {
            UserEntity userEntity = userToDocument(preferences.getUser());
            List<ConsentEntity> consentEntities = preferences.getConsents().stream()
                    .map(PreferencesEntityMapper::consentToDocument)
                    .toList();
            return new PreferencesEntity(userEntity, consentEntities);
        }

        private static User userToDom(UserEntity userEntity) {
            return new User(userEntity.getId(), userEntity.getEmail());
        }

        private static Consent consentToDom(ConsentEntity consentEntity) {
            return new Consent(consentEntity.getId(), consentEntity.isEnabled());
        }

        private static UserEntity userToDocument(User user) {
            return new UserEntity(user.getId(), user.getEmail());
        }

        private static ConsentEntity consentToDocument(Consent consent) {
            return new ConsentEntity(consent.getId(), consent.isEnabled());
        }
}
