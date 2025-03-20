package com.vw.preferences.domain.model.preferences;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private final String id;
    private  String email;
}
