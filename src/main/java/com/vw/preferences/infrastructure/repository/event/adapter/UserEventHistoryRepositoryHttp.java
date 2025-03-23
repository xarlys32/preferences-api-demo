package com.vw.preferences.infrastructure.repository.event.adapter;

import com.mongodb.MongoInterruptedException;
import com.vw.preferences.domain.model.event.UserEventHistory;
import com.vw.preferences.domain.port.event.UserEventHistoryRepository;
import com.vw.preferences.infrastructure.repository.event.entity.UserEventHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserEventHistoryRepositoryHttp implements UserEventHistoryRepository {

    private final UserEventHistoryMongoRepository userEventHistoryMongoRepository;
    private final UserEventHistoryEntityMapper userEventHistoryEntityMapper;

    public UserEventHistoryRepositoryHttp(UserEventHistoryMongoRepository preferencesMongoRepository,
                                          UserEventHistoryEntityMapper preferencesEntityMapper) {
        this.userEventHistoryMongoRepository = preferencesMongoRepository;
        this.userEventHistoryEntityMapper = preferencesEntityMapper;
    }

    public UserEventHistory save(UserEventHistory history) throws MongoInterruptedException  {
        UserEventHistoryEntity userEventHistoryEntity = userEventHistoryEntityMapper.toDocument(history);

        return userEventHistoryEntityMapper.toDom(userEventHistoryMongoRepository.save(userEventHistoryEntity));
    }


    public UserEventHistory getHistoryByUser(String userId) {
        UserEventHistoryEntity entity = userEventHistoryMongoRepository.findByUserId(userId);
        return entity != null ? userEventHistoryEntityMapper.toDom(userEventHistoryMongoRepository.findByUserId(userId)) :
                null;
    }


}
