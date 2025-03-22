package com.vw.preferences.infrastructure.repository.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEventHistoryEntity {
    private String userId;
    private List<ConsentHistoryEntity> consentHistoryList;
}
