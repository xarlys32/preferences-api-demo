package com.vw.preferences.infrastructure.repository.user.adapter;

import com.mongodb.MongoInterruptedException;
import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.port.user.UserRepository;
import com.vw.preferences.infrastructure.repository.user.entity.ConsentEntity;
import com.vw.preferences.infrastructure.repository.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryHttp implements UserRepository {

    private final UserMongoRepository userMongoRepository;
    private final UserEntityMapper userEntityMapper;

    public UserRepositoryHttp(UserMongoRepository preferencesMongoRepository,
                              UserEntityMapper preferencesEntityMapper) {
        this.userMongoRepository = preferencesMongoRepository;
        this.userEntityMapper = preferencesEntityMapper;
    }

    public User save(User preferences) throws MongoInterruptedException {
        UserEntity preferencesEntity = userEntityMapper.toDocument(preferences);

        return userEntityMapper.toDom(userMongoRepository.save(preferencesEntity));
    }

    public User createAccount(String mail) throws MongoInterruptedException {
        UserEntity newUserPreferenceEntity = createUserByEmail(mail);
        newUserPreferenceEntity.setConsents(createConsentForNewUser());

        return userEntityMapper.toDom(userMongoRepository.save(newUserPreferenceEntity));
    }

    public User getPreferencesByUser(String email) {
        UserEntity userEntity = userMongoRepository.findByEmail(email);
        if (userEntity == null) {
            return null;
        }
        return userEntityMapper.toDom(userMongoRepository.findByEmail(email));
    }

    private UserEntity createUserByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        return userEntity;
    }

    private List<ConsentEntity> createConsentForNewUser() {
        return new ArrayList<>();
    }
}
