package com.vw.preferences.infrastructure.repository.preferences.adapter;

import com.vw.preferences.domain.model.preferences.Preferences;
import com.vw.preferences.domain.port.preferences.PreferencesRepository;
import com.vw.preferences.infrastructure.repository.preferences.entity.PreferencesEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PreferencesRepositoryHttp implements PreferencesRepository {

    private final PreferencesMongoRepository preferencesMongoRepository;
    private final PreferencesEntityMapper preferencesEntityMapper;

    public PreferencesRepositoryHttp(PreferencesMongoRepository preferencesMongoRepository,
                                     PreferencesEntityMapper preferencesEntityMapper) {
        this.preferencesMongoRepository = preferencesMongoRepository;
        this.preferencesEntityMapper = preferencesEntityMapper;
    }

    @Override
    public void save(Preferences preferences) {
        PreferencesEntity preferencesEntity = preferencesEntityMapper.toDocument(preferences);

        preferencesMongoRepository.save(preferencesEntity);
    }

    @Override
    public Preferences getPreferencesByUser(String userId) {
        return null;
    }
}
