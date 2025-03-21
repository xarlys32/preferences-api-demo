package com.vw.preferences.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Consent {
    private String consentId;
    private boolean enabled;
}
