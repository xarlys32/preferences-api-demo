package com.vw.preferences.domain.model.preferences;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Consent {
    private final String id;
    private boolean enabled;
}
