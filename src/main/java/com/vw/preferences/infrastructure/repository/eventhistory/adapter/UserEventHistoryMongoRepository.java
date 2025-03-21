package com.vw.preferences.infrastructure.repository.eventhistory.adapter;

import com.vw.preferences.infrastructure.repository.eventhistory.entity.UserEventHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEventHistoryMongoRepository extends MongoRepository<UserEventHistoryEntity, String > {
}
