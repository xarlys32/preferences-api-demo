package com.vw.preferences.infrastructure.repository.user.adapter;

import com.vw.preferences.infrastructure.repository.user.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserEntity, String > {
    UserEntity findByEmail(String email);
}
