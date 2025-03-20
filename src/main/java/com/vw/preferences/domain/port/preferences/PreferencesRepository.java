package com.vw.preferences.domain.port.preferences;

import com.vw.preferences.domain.model.preferences.Preferences;

public interface PreferencesRepository {
    void save(Preferences preferences);
    Preferences getPreferencesByUser(String userId);
}
