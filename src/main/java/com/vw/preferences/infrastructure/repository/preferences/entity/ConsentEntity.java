package com.vw.preferences.infrastructure.repository.preferences.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class ConsentEntity {
    private final String id;
    private boolean enabled;
}
