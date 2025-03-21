package com.vw.preferences.infrastructure.repository.eventhistory.adapter;

import com.vw.preferences.domain.model.eventhistory.UserEventHistory;
import com.vw.preferences.domain.port.eventshistory.UserEventHistoryRepository;
import com.vw.preferences.infrastructure.repository.eventhistory.entity.UserEventHistoryEntity;
import com.vw.preferences.infrastructure.repository.user.entity.ConsentEntity;
import com.vw.preferences.infrastructure.repository.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEventHistoryRepositoryHttp implements UserEventHistoryRepository {

    private final UserEventHistoryMongoRepository userEventHistoryMongoRepository;
    private final UserEventHistoryEntityMapper userEventHistoryEntityMapper;

    public UserEventHistoryRepositoryHttp(UserEventHistoryMongoRepository preferencesMongoRepository,
                                          UserEventHistoryEntityMapper preferencesEntityMapper) {
        this.userEventHistoryMongoRepository = preferencesMongoRepository;
        this.userEventHistoryEntityMapper = preferencesEntityMapper;
    }

    @Override
    public UserEventHistory save(UserEventHistory history) {
        UserEventHistoryEntity userEventHistoryEntity = userEventHistoryEntityMapper.toDocument(history);

        return userEventHistoryEntityMapper.toDom(userEventHistoryMongoRepository.save(userEventHistoryEntity));
    }

    @Override
    public UserEventHistory createHistory(String mail) {
        // Deberia recibir el User de dominio recien creado
        UserEventHistoryEntity newUserPreferenceEntity = new UserEventHistoryEntity();
        newUserPreferenceEntity.setUserEntity(createUserByEmail(mail));
        newUserPreferenceEntity.setConsents(createConsentForNewUser());

        return userEventHistoryEntityMapper.toDom(userEventHistoryMongoRepository.save(newUserPreferenceEntity));
    }

    @Override
    public UserEventHistory getHistoryByUser(String userId) {
        return null;
    }

    private UserEntity createUserByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        return userEntity;
    }

}
