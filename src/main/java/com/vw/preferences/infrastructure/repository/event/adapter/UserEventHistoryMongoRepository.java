package com.vw.preferences.infrastructure.repository.event.adapter;

import com.vw.preferences.infrastructure.repository.event.entity.UserEventHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEventHistoryMongoRepository extends MongoRepository<UserEventHistoryEntity, String > {
    UserEventHistoryEntity findByUserId(String userId);
}
