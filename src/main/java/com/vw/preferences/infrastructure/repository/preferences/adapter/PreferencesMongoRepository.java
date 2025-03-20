package com.vw.preferences.infrastructure.repository.preferences.adapter;

import com.vw.preferences.infrastructure.repository.preferences.entity.PreferencesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PreferencesMongoRepository extends MongoRepository<PreferencesEntity, String > {
}
