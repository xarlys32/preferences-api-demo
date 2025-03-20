package com.vw.preferences.domain.model.preferences;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Preferences {
    private User user;
    private List<Consent> Consents;
}
