package com.vw.preferences.infrastructure.repository.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class ConsentEntity {
    private String consentId;
    private boolean enabled;
}
