package com.vw.preferences.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String email;

    private List<Consent> Consents;
}
