package com.vw.preferences.domain.usecase.user;

import com.vw.preferences.domain.model.user.Consent;

public record PostConsentUpdate(String email, Consent consent) {
}
