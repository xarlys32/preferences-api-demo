package com.vw.preferences.domain.usecase.event;

import com.vw.preferences.domain.model.user.Consent;

public record PostConsentEvent(String userId, Consent consent) {
}
