package com.vw.preferences.domain.usescase.preferences;

import com.vw.preferences.domain.model.preferences.Preferences;
import com.vw.preferences.domain.port.preferences.PreferencesRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class PreferencesUseCases {

    private final PreferencesRepository preferencesRepo;

    public PreferencesUseCases(PreferencesRepository preferencesRepo) {
        this.preferencesRepo = preferencesRepo;
    }

    @QueryHandler
    public Preferences getPreferences(GetPreferences query) {
        return preferencesRepo.getPreferencesByUser(query.userId());

    }
}
