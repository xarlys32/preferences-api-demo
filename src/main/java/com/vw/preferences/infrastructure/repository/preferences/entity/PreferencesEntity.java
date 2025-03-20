package com.vw.preferences.infrastructure.repository.preferences.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@Document
public class PreferencesEntity {
    private UserEntity userEntity;
    private List<ConsentEntity> Consents;
}
