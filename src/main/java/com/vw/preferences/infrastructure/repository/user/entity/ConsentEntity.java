package com.vw.preferences.infrastructure.repository.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsentEntity {
    @Field(name = "consent_id")
    private String consentId;
    @Field(name = "enabled")
    private boolean enabled;
}
