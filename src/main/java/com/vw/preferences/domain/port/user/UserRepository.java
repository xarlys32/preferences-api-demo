package com.vw.preferences.domain.port.user;

import com.vw.preferences.domain.model.user.User;

public interface UserRepository {
    User save(User preferences);
    User createAccount(String mail);
    User getPreferencesByUser(String email);
}
