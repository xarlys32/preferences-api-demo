package com.vw.preferences.infrastructure.repository.user.adapter;

import com.vw.preferences.domain.model.user.User;
import com.vw.preferences.domain.port.user.UserRepository;
import com.vw.preferences.infrastructure.repository.user.entity.ConsentEntity;
import com.vw.preferences.infrastructure.repository.user.entity.UserEntity;
import org.springframework.stereotype.Repository;

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

    @Override
    public User save(User preferences) {
        UserEntity preferencesEntity = userEntityMapper.toDocument(preferences);

        return userEntityMapper.toDom(userMongoRepository.save(preferencesEntity));
    }

    @Override
    public User createAccount(String mail) {
        UserEntity newUserPreferenceEntity = createUserByEmail(mail);
        newUserPreferenceEntity.setConsents(createConsentForNewUser());

        return userEntityMapper.toDom(userMongoRepository.save(newUserPreferenceEntity));
    }

    public User getPreferencesByUser(String userId) {
        return null;
    }

    private UserEntity createUserByEmail(String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        return userEntity;
    }

    private List<ConsentEntity> createConsentForNewUser() {
        return List.of();
    }
}
